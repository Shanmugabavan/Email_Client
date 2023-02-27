// your index number

//import libraries

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;
//==================================================EmailClient===========================================================
public class Email_Client {

    public static void creatObject(String data,ArrayList birthdayList,ArrayList personalList,ArrayList officialList,ArrayList officeFriendList){
        try {
            String[] dataSplitArray1 = data.split(":");
            String[] dataSplitArray2 = dataSplitArray1[1].split(",");
            //System.out.println(data);

            if (data.startsWith("Office_friend:")) {
                String name = dataSplitArray2[0];
                String email = dataSplitArray2[1];
                String designation = dataSplitArray2[2];
                String dob = dataSplitArray2[3];
                OfficeFriendRecipient res = new OfficeFriendRecipient(name, email, designation, dob);
                BirthdayWishable bdres=new OfficeFriendRecipient(name,email,designation,dob);
                officeFriendList.add(res);
                officialList.add(res);
                birthdayList.add(bdres);



            } else if (data.startsWith("Personal")) {
                String name = dataSplitArray2[0];
                String nickname = dataSplitArray2[1];
                String email = dataSplitArray2[2];
                String dob = dataSplitArray2[3];
                Personal_Recipient res= new Personal_Recipient(name,email,nickname,dob);
                BirthdayWishable bdres=new Personal_Recipient(name,email,nickname,dob);
                personalList.add(res);
                birthdayList.add(bdres);


            } else if (data.startsWith("Official:")) {
                String name = dataSplitArray2[0];
                String email = dataSplitArray2[1];
                String designation = dataSplitArray2[2];
                Official_Recipient res= new Official_Recipient(name,email,designation);
                officialList.add(res);
            }
        } catch (Exception e){}
    }

    //==========================================WriteFunction========================================================================
    public static void write(String content,String path){
        try {
            File file = new File(path);

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //=======================================Write ObjectFunction=========================================================================
    public static void writeOobject(ArrayList<Email> emailhistorylist) throws IOException {
        String pathfile="emailHistory.txt";
        FileOutputStream fos = new FileOutputStream(pathfile);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(emailhistorylist);
        oos.close();
    }

    //=======================================Read ObjectFunction==========================================================================

    //==========================================emailSending==============================================================================
    public  static void emailSender(Email email,ArrayList<Email> emailHistoryList) throws IOException {
        // code to send an email//////////////////////////////////////////////
        final String username = "";
        final String password = "";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("nimalpereracs@gmail.com"));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(email.getEmailName())
            );
            message.setSubject(email.getSubject());
            message.setText(email.getSubject()
                    + "\n  "+ email.getContent());

            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        String nowtime=getTime();
        email.setDate(nowtime);
        emailHistoryList.add(email);
        writeOobject(emailHistoryList);

    }
    //==================================================getTime================================================================================
    public static String getTime(){
        LocalDate myObj = LocalDate.now(); // Create a date object
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        String formattedDate = myObj.format(myFormatObj);
        return(formattedDate); // Display the current date
    }
    //Receiving_Mail_append and read//
    public static ArrayList<Received_Mail> readAndAppend() throws IOException {
        File file=new File("Received_Mail_Objects.txt");
        ArrayList<Received_Mail> list=new ArrayList<>();
        if (!file.exists()){
            file.createNewFile();
        }
        if (file.length()!=0) {
            FileInputStream inputStream = new FileInputStream(file);
            ObjectInputStream ostream = new ObjectInputStream(inputStream);
            while (true) {
                try {
                    Received_Mail c = (Received_Mail) ostream.readObject();
                    list.add(c);
                } catch (EOFException | ClassNotFoundException e) {
                    break;
                }
            }
            ostream.close();
        }
        return list;
    }
    //==================================================MainFunction========================================================================
    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException,EOFException {
        Email_State_Printer email_state_printer=new Email_State_Printer();
        Email_State_Recorder emailStateRecorder=new Email_State_Recorder();
        MyBlockingQueue queue=new MyBlockingQueue();
        Manager manager = new Manager(queue);

        File files = new File("Received_Emails_File.txt");
        if (!files.exists()){
            files.createNewFile();
        }
        BufferedReader br = new BufferedReader(new FileReader(files));
        String st;
        int count=0;
        while ((st = br.readLine()) != null)
            count+=1;

        Email_Receiver email_receiver=new Email_Receiver(count,emailStateRecorder,email_state_printer,readAndAppend(),queue);
        email_receiver.start();
        manager.start();
        //////////////////for append old dataList to running programme;//////////////////////1

        String pathfff="Clients_List.txt";
        File file= new File (pathfff);
        Scanner sc = new Scanner (file);

        ArrayList<String> dataList = new ArrayList<String>( );
        ArrayList<BirthdayWishable> birthdayList = new ArrayList<BirthdayWishable>();
        ArrayList<Personal_Recipient>personalList=new ArrayList<Personal_Recipient>();
        ArrayList<Official_Recipient>officialList=new ArrayList<Official_Recipient>();
        ArrayList<OfficeFriendRecipient> officeFriendList=new ArrayList<OfficeFriendRecipient>();
        ArrayList<Email> emailHistoryList=new ArrayList<Email>();
        while (sc.hasNextLine()) {
            dataList.add(sc.nextLine());
        }
        for (int i=0; i< dataList.size();i++){
            creatObject(dataList.get(i),birthdayList,personalList,officialList,officeFriendList);
        }
        /////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////read object and save into ArrayList<email>/////////////////////////

        try {
            String pathff = "emailHistory.txt";
            FileInputStream fileInn = new FileInputStream(pathff);
            ObjectInputStream objectIn = new ObjectInputStream(fileInn);
            try {
                while (true) {
                    ArrayList<Email> emailn = (ArrayList<Email>) objectIn.readObject();
                    emailHistoryList = emailn;
                }
            } catch (Exception e) {
                e.getStackTrace();
            }
            objectIn.close();
        }catch (Exception e){};
        //////////////////////////////////////////////////////////////////////////////////////////
        for(int i=0;i<birthdayList.size();i++){
            if (getTime().substring(4).equals(birthdayList.get(i).getDob().substring(4))){
                String emailName=birthdayList.get(i).getEmail();
                String subject="BD wish";
                String bdWish=birthdayList.get(i).bdWish();
                Email emailbb=new Email(emailName,bdWish,subject);
                emailSender(emailbb,emailHistoryList);
            }
        }
        System.out.println(birthdayList);
        ////////////////////////////////////////////////////////////////////////////////////////////
        Scanner scanner = new Scanner(System.in);
        while (true) {

            System.out.println("Enter option type: \n"
                    + "1 - Adding a new recipient - \n      Official: nimal,nimal@gmail.com,ceo\n      Office_friend: kamal,kamal@gmail.com,clerk,2000/12/12\n      Personal: sunil,<nick-name>,sunil@gmail.com,2000/10/10\n"
                    + "2 - Sending an email\n      input format - email, subject, content\n"
                    + "3 - Printing out all the recipients who have birthdays\n"
                    + "4 - Printing out details of all the emails sent\n"
                    + "5 - Printing out the number of recipient objects in the application");

            int option = scanner.nextInt();


            switch (option) {
                case 1:
                    // input format - Official: nimal,nimal@gmail.com,ceo
                    // Use a single input to get all the details of a recipient
                    // code to add a new recipient
                    // store details in clientList.txt file
                    // Hint: use methods for reading and writing files
                    System.out.println("Input: detail");
                    Scanner sca = new Scanner(System.in);
                    String newData = sca.nextLine();
                    creatObject(newData, birthdayList, personalList, officialList, officeFriendList);
                    String path="Clients_List.txt";
                    write(newData + '\n',path);

                    if (birthdayList.get(birthdayList.size()-1).getDob().equals(getTime())){
                        String emailName=birthdayList.get(birthdayList.size()-1).getEmail();
                        String subject="BD wish";
                        String bdWish=birthdayList.get(birthdayList.size()-1).bdWish();
                        Email emailb=new Email(emailName,bdWish,subject);
                        emailSender(emailb,emailHistoryList);
                    }
                    break;
                case 2:
                    // input format - email, subject, content
                    Scanner case2 = new Scanner(System.in);
                    String input=case2.nextLine();
                    String[] inputArray=input.split(",");
                    String emailName=inputArray[0];
                    String subject=inputArray[1];
                    String content=inputArray[2];
                    Email email=new Email(emailName,content,subject);
                    emailSender(email,emailHistoryList);
                    System.out.println("Email has been sent");

                    break;
                case 3:
                    // input format - yyyy/MM/dd (ex: 2018/09/17)
                    // code to print recipients who have birthdays on the given date
                    Scanner dateObject1=new Scanner(System.in);
                    String date1=dateObject1.nextLine();
                    int indicator=0;
                    for (int i=0;i<birthdayList.size();i++){
                        if (date1.equals(birthdayList.get(i).getDob())) {
                            System.out.println(birthdayList.get(i).getName());
                            indicator++;
                        }
                    }
                    if (indicator==0) {
                        System.out.println("No one have birthday on " + date1);
                    }

                    break;
                case 4:
                    // input format - yyyy/MM/dd (ex: 2018/09/17)
                    // code to print the details of all the emails sent on the input date
                    Scanner dateObject2=new Scanner(System.in);
                    String date=dateObject2.nextLine();
                    int indicator2=0;


                    for (int i=0; i< emailHistoryList.size();i++){
                        if (date.equals(emailHistoryList.get(i).getDate())) {
                            System.out.println(emailHistoryList.get(i).getEmailName()+" "+emailHistoryList.get(i).getSubject()+" "+emailHistoryList.get(i).getContent()+" "+emailHistoryList.get(i).getDate());
                            indicator2++;
                        }
                    }
                    if (indicator2==0){
                        System.out.println("No email has been sent on "+date);
                    }

                    break;
                case 5:
                    // officeFriendList.add(res);
                    //                officialList.add(res);
                    //                birthdayList.add(bdres);
                    System.out.println(officialList.size()+personalList.size());
                    System.out.println(emailHistoryList);
                    break;

            }
        }


        // start email client
        // code to create objects for each recipient in clientList.txt
        // use necessary variables, methods and classes
    }
}

// create more classes needed for the implementation (remove the  public access modifier from classes when you submit your code)

