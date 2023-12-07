import java.io.Serial;
import java.io.Serializable;
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    private boolean isAdmin;

    public User(String username, String password, boolean isAdmin) {


        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public String getPassword() {
        return password;
    }
    public boolean isAdmin(){
        return isAdmin;
    }


}