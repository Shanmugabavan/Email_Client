public class Email_State_Recorder implements Observer {
    public Email_State_Recorder() {
    }

    @Override
    public void update(Received_Mail newly_received_Mail) {
        System.out.println("an email is received at "+newly_received_Mail.getDate()+"<<From-"+newly_received_Mail.getFrom()+">><<Subject-"+newly_received_Mail.getSubject()+">><<Content-"+newly_received_Mail.getContent()+">>");
    }
}
