import java.io.*;
import java.util.ArrayList;

public class Manager extends Thread {
    private MyBlockingQueue queue;
    public Manager(MyBlockingQueue queue){
        this.queue=queue;
    }
    public void run() {
        while (!interrupted()) {
            try {
                ArrayList<Received_Mail> list = process();
                list.add(pick());
                serializedMail(list);
                //Thread.sleep(10000);
            } catch (IOException | InterruptedException e) {
                break;
            }

        }
    }

    private Received_Mail pick() throws InterruptedException {
        return queue.dequeue();
    }

    private void serializedMail(ArrayList<Received_Mail> old_received_mails_list) throws IOException {   //serialize mail
        try {

            FileOutputStream fileOut = new FileOutputStream("Received_Mail_Objects.txt");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            for (int i=0;i<old_received_mails_list.size();i++){
                objectOut.writeObject(old_received_mails_list.get(i));
            }

            objectOut.close();
            //System.out.println("The Object  was succesfully written to a file");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private ArrayList<Received_Mail> process() throws IOException {
        File rmfile=new File("Received_Mail_Objects.txt");
        ArrayList<Received_Mail> arr=new ArrayList<>();
        if (!rmfile.exists()){
            rmfile.createNewFile();
        }
        if (rmfile.length()!=0){
            FileInputStream fi = new FileInputStream(rmfile);
            ObjectInputStream oi = new ObjectInputStream(fi);
            while (true) {
                try {
                    Received_Mail m = (Received_Mail) oi.readObject();
                    arr.add(m);
                } catch (EOFException | ClassNotFoundException e) {
                    break;
                }
            }
        }
        return arr;
    }

}
