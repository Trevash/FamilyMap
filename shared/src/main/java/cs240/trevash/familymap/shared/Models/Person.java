package cs240.trevash.familymap.shared.Models;

/**
 * Created by trevash on 2/13/18.
 */

/**
 * A Person object is the main objects within the family map tree.
 * Persons can have event objects and other Person objects tied to them.
 */
public class Person {
    private String error;
    private String personID;//Can't be empty
    private String descendant;//Can't be empty
    private String firstName;//Can't be empty
    private String lastName;//Can't be empty
    private String gender;//Can't be empty
    private String father;//nullable
    private String mother;//nullable
    private String spouse;//nullable

    public Person(String personID, String DU, String FN, String LN, String G, String FID, String MID, String SID) {
        this.personID = personID;
        this.descendant = DU;
        this.firstName = FN;
        this.lastName = LN;
        this.gender = G;
        this.father = FID;
        this.mother = MID;
        this.spouse = SID;
    }

    public Person(String personID, String DU, String FN, String LN, String G, String FID, String MID) {
        this.personID = personID;
        this.descendant = DU;
        this.firstName = FN;
        this.lastName = LN;
        this.gender = G;
        this.father = FID;
        this.mother = MID;
    }

    public Person(String personID, String DU, String FN, String LN, String G) {
        this.personID = personID;
        this.descendant = DU;
        this.firstName = FN;
        this.lastName = LN;
        this.gender = G;
    }

    public Person(String personID, String DU, String FN, String LN, String G, String SID) {
        this.personID = personID;
        this.descendant = DU;
        this.firstName = FN;
        this.lastName = LN;
        this.gender = G;
        this.spouse = SID;
    }

    public Person(String error) {
        this.error = error;
    }

    public String getPersonId() {
        return personID;
    }

    public String getDescendantUsername() {
        return descendant;
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

    public String getFatherId() {
        return father;
    }

    public String getMotherId() {
        return mother;
    }

    public String getSpouseId() {
        return spouse;
    }

    public String getError() {
        return error;
    }

    public void setFatherId(String father) {
        this.father = father;
    }

    public void setMotherId(String mother) {
        this.mother = mother;
    }

    public void setSpouseId(String spouse) {
        this.spouse = spouse;
    }

    public void setPersonId(String personID) {
        this.personID = personID;
    }
}
