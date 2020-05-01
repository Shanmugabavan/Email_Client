import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

public class Email_Receiver extends Thread {
    private static int count=0;
    private int true_count=0;
    private ArrayList<Observer> observers_list=new ArrayList<>();
    private ArrayList <Received_Mail> old_received_mails_list;
    private MyBlockingQueue Queue;

    public Email_Receiver(int count, Email_State_Recorder email_state_recorder,Email_State_Printer email_state_printer, ArrayList<Received_Mail> old_received_mails_list,MyBlockingQueue Queue) {
        this.count = count;
        this.observers_list.add(email_state_printer);
        this.observers_list.add(email_state_recorder);
        this.old_received_mails_list = old_received_mails_list;
        this.Queue=Queue;
    }
    //===================================================================================================================

    //=====================Receiving_Big_Part===============================================================================
    private Properties getServerProperties(String protocol, String host,
                                           String port) {
        Properties properties = new Properties();
        properties.put(String.format("mail.%s.host", protocol), host);
        properties.put(String.format("mail.%s.port", protocol), port);
        properties.setProperty(
                String.format("mail.%s.socketFactory.class", protocol),
                "javax.net.ssl.SSLSocketFactory");
        properties.setProperty(
                String.format("mail.%s.socketFactory.fallback", protocol),
                "false");
        properties.setProperty(
                String.format("mail.%s.socketFactory.port", protocol),
                String.valueOf(port));
        return properties;
    }

    public void downloadEmails(String protocol, String host, String port,
                               String userName, String password) {
        Properties properties = getServerProperties(protocol, host, port);
        Session session = Session.getDefaultInstance(properties);

        try {
            Store store = session.getStore(protocol);
            store.connect(userName, password);
            Folder folderInbox = store.getFolder("INBOX");
            folderInbox.open(Folder.READ_ONLY);
            this.true_count=folderInbox.getMessageCount();

            if (count!=true_count) {
                Message[] messages_list=folderInbox.getMessages();
                for (int i = count; i <true_count; i++) {
                    Message msg = messages_list[i];
                    Address[] fromAddress = msg.getFrom();
                    String from = fromAddress[0].toString();
                    String subject = msg.getSubject();
                    String sentDate = msg.getSentDate().toString();
                    String contentType = msg.getContentType();
                    String messageContent = "";
                    if (contentType.contains("text/plain")
                            || contentType.contains("text/html")) {
                        try {
                            Object content = msg.getContent();
                            if (content != null) {
                                messageContent = content.toString();
                            }
                        } catch (Exception ex) {
                            messageContent = "[Error downloading content]";
                            ex.printStackTrace();
                        }
                    }
                    Received_Mail received_mail = new Received_Mail(sentDate, from, subject, messageContent);
                    old_received_mails_list.add(received_mail);
                    this.alerting(received_mail);
                    Queue.enqueue(received_mail);

                }
                count=true_count;
            }
            folderInbox.close(false);
            store.close();
        } catch (NoSuchProviderException ex) {
            System.out.println("No provider for protocol: " + protocol);
            ex.printStackTrace();
        } catch (MessagingException | IOException ex) {
            System.out.println("Could not connect to the message store");
            ex.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //==================================================================================================================
    public void alerting(Received_Mail newly_received_Mail) throws IOException {
        for (int i=0;i<observers_list.size();i++){
            observers_list.get(i).update(newly_received_Mail);
            File file=new File("Received_Mail_Objects.txt");
            if (!file.exists()){
                file.createNewFile();
            }
        }
    }
    //======================================Add_Del_Observers===========================================
    public void addObservers(Observer observer){
        observers_list.add(observer);
    }
    public void del_observers(Observer observer){
        observers_list.remove(observer);
    }
    //===================================================================================================================

    public void run(){
        String protocol = "imap";
        String host = "imap.gmail.com";
        String port = "993";
        String userName = "shanmugakumar.18@cse.mrt.ac.lk";
        String password = "Shanmu@25621";
        while (true){
            downloadEmails(protocol,host,port,userName,password);
        }
    }

}
