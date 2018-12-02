package cs240.trevash.familymap.shared.Objects;

import java.util.UUID;

/**
 * Created by trevash on 2/14/18.
 */

/**
 * Creates a result for when a user successfully registers or logs in
 */
public class RegisterLoginResult {
    private UUID authToken;
    private String userName;
    private String personID;
    private String message;

    /**
     * Constructs a register or login result object
     * @param CA - current authorization for user
     * @param UN - userName of user
     * @param PID - personID of user
     */
    public RegisterLoginResult(UUID CA, String UN, String PID) {
        this.authToken = CA;
        this.userName = UN;
        this.personID = PID;
    }

    public RegisterLoginResult(String error) {
        this.message = error;
    }

    public UUID getCurrAuth() {
        return authToken;
    }

    public String getUserName() {
        return userName;
    }

    public String getPersonId() {
        return personID;
    }

    public String getMessage() {
        return message;
    }
}
