import java.io.*;

public class WriteObject {
    private String pathf;
    public void main(String[] args) throws IOException {
        String pathf="C:\\Users\\Shanmu\\IdeaProjects\\Email_Client\\src\\shanmu.txt";
        Official_Recipient res=new Official_Recipient("shnamu","shnamugabavan","engi");
        Official_Recipient resn=new Official_Recipient("shnamu","shnamugabavan","engi");
        FileOutputStream f = new FileOutputStream(new File("myObjects.txt"));
        ObjectOutputStream o = new ObjectOutputStream(f);

        // Write objects to file
        o.writeObject(res);
        o.writeObject(resn);

        o.close();
        f.close();
    }
}
