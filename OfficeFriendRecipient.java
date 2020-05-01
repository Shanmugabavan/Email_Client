public class OfficeFriendRecipient extends Official_Recipient implements BirthdayWishable {
    String dob;

    public OfficeFriendRecipient(String name, String email, String designation, String dob) {
        super(name, email, designation);
        this.dob = dob;
    }

    ///////////////////////getters//////////////////////

    public String getDob() {
        return dob;
    }
    public String bdWish(){
        return ("Wish You a Happy Birthday. <Shanmugabavan>");
    }

}
