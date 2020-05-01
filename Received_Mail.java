import java.io.Serializable;

public class Received_Mail implements Serializable {
    private String date;
    private String from;
    private String subject;
    private String content;

    public Received_Mail(String date, String from, String subject, String content) {
        this.date = date;
        this.from = from;
        this.subject = subject;
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
