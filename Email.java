import java.io.Serializable;

public class Email implements Serializable {
    private String emailName;
    private String content;
    private String date;
    private String subject;

    public Email(String emailName, String content, String subject) {
        this.emailName = emailName;
        this.content = content;
        this.subject = subject;
    }

    ////////////////////////setters///////////////////////////////////

    public void setDate(String date) {
        this.date = date;
    }

///////////////////////getter/////////////////////////////////////

    public String getEmailName() {
        return emailName;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }

    public String getSubject() {
        return subject;
    }
}
