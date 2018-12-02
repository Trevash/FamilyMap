package cs240.trevash.familymap.shared.Objects;

/**
 * Created by trevash on 2/14/18.
 */

/**
 * This is the request object used by register requests. This information will be used as
 * the program determines whether or not a user has input correct information to register
 */
public class RegisterRequest {
    private String personID;//Can't be empty
    private String userName;//Can't be empty
    private String password;//Can't be empty
    private String emailAddress;//Can't be empty
    private String firstName;//Can't be empty
    private String lastName;//Can't be empty
    private String gender;//Can't be empty

    /**
     * Constructs request object for Register Request
     * @param UN - userName
     * @param PW - password
     * @param EA - email address
     * @param FN - first name
     * @param LN - last name
     * @param G - gender
     */

    public RegisterRequest(String UN, String PW, String EA, String FN, String LN, String G) {
        this.userName = UN;
        this.password = PW;
        this.emailAddress = EA;
        this.firstName = FN;
        this.lastName = LN;
        this.gender = G;
    }

    public RegisterRequest(String UN) {
        this.userName = UN;
    }

    public String getPersonId() {
        return personID;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }
}
