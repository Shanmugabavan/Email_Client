public abstract class Recipient {
    String name;
    String email;

    public Recipient(String name, String email) {
        this.name = name;
        this.email = email;
    }
    ////////////////////////getters//////////////////////


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
