public class Personal_Recipient extends Recipient implements BirthdayWishable{
    String nickname;
    String dob;

    public Personal_Recipient(String name, String email, String nickname, String dob) {
        super(name, email);
        this.nickname = nickname;
        this.dob = dob;
    }

    ///////////////////////////getters//////////////////////

    public String getNickname() {
        return nickname;
    }

    public String getDob() {
        return dob;
    }
    public String bdWish(){
        return ("hugs and love on your birthday. <Shanmugabavan>");
    }

}
