package cs240.trevash.familymap.shared.Objects;

/**
 * Created by trevash on 2/14/18.
 */

/**
 * Class to create a login request object for a user logging in
 */
public class LoginRequest {

    private String userName;
    private String password;

    /**
     * Constructs request object for Register LoginHandler
     * @param UN - userName
     * @param PW - password
     */

    public LoginRequest(String UN, String PW) {
        this.userName = UN;
        this.password = PW;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
