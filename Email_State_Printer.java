import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class Email_State_Printer implements Observer {
    public Email_State_Printer() {
    }

    @Override
    public void update(Received_Mail newly_received_Mail) {
        File file=new File("Received_Emails_File.txt");
        File file2=new File("Received_Emails_Object.txt");
        if (true) {
            try {
                file.createNewFile();
                FileWriter writer=new FileWriter(file,true);
                String s=("an email is received at "+newly_received_Mail.getDate()+"<<From-"+newly_received_Mail.getFrom()+">><<Subject-"+newly_received_Mail.getSubject()+">><<Content-"+newly_received_Mail.getContent()+">>");
                writer.write(s);
                writer.write("\n");
                writer.close();
            } catch (IOException e) {
                System.out.println("Input File error");
            }
        }


    }
}
