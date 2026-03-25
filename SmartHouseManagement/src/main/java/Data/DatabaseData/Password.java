package Data.DatabaseData;

public class Password {
    private final Integer id;
    private  String password;

    public Password(Integer id, String password) {
        this.id = id;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }
    public String getPassword() {
        return password;
    }
    public void changePassword(String newPassword) {
        this.password = newPassword;
    }
}
