package cs240.trevash.familymap.shared.Models;

/**
 * Created by trevash on 2/13/18.
 */

/**
 * A user object is related to a new or current user that is a root of a tree.
 * Each user will have multiple Person and Event objects attached to it.
 */
public class User {

    private String personID;//Can't be empty
    private String userName;//Can't be empty
    private String password;//Can't be empty
    private String emailAddress;//Can't be empty
    private String firstName;//Can't be empty
    private String lastName;//Can't be empty
    private String gender;//Can't be empty

    public User(String PID, String UN, String PW, String EA, String FN, String LN, String G) {
        this.personID = PID;
        this.userName = UN;
        this.password = PW;
        this.emailAddress = EA;
        this.firstName = FN;
        this.lastName = LN;
        this.gender = G;
    }

    public User(String UN, String PW, String EA, String FN, String LN, String G) {
        this.userName = UN;
        this.password = PW;
        this.emailAddress = EA;
        this.firstName = FN;
        this.lastName = LN;
        this.gender = G;
    }

    public String getPersonId() {
        return personID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public void setPersonId(String personID) {
        this.personID = personID;
    }
}
