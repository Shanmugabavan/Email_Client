public class Official_Recipient extends Recipient{
    String designation;

    public Official_Recipient(String name, String email, String designation) {
        super(name, email);
        this.designation = designation;
    }

    ////////////////////////getters////////////////////////

    public String getDesignation() {
        return designation;
    }
}
