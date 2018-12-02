package cs240.trevash.familymap.shared.Services;

import org.junit.Test;

import java.util.ArrayList;

import cs240.trevash.familymap.shared.Models.Person;
import cs240.trevash.familymap.shared.Objects.EventPersonList;
import cs240.trevash.familymap.shared.Objects.RegisterRequest;

import static org.junit.Assert.assertTrue;

/**
 * Created by trevash on 3/5/18.
 */

public class generatePersonTest {

    private generatePerson genPerson = new generatePerson();

    @Test
    public void GeneratePersonsAndEventsForUserName() throws Exception {
        int generations = 4;
        RegisterRequest registerRequest = new RegisterRequest("UName", "PW", "EA", "FN", "LN", "M");
        EventPersonList eventPersonList = genPerson.generatePersonsAndEventsForUser(generations, registerRequest);
        assertTrue(eventPersonList.getEvents().size() > 0 && eventPersonList.getPersons().size() > 0);
    }


    @Test
    public void generateForSQLTest() {
        ArrayList<Person> persons = new ArrayList<>();
        Person person = new Person("PID", "dUName", "FName", "LName", "G", "fId", "mId", "sId");
        Person person1 = new Person("PID1", "dUName", "FName1", "LName", "G", "sId");
        Person person2 = new Person("PID1", "dUName", "FName1", "LName", "G", "fId", "mId");
        Person person3 = new Person("PID1", "dUName", "FName1", "LName", "G");

        persons.add(person);
        persons.add(person1);
        persons.add(person2);
        persons.add(person3);

        ArrayList<String> generatedPersons = genPerson.generateForSQL(persons);
        assertTrue(generatedPersons.get(0).length() > 0 && generatedPersons.get(1).length() > 0 && generatedPersons.get(2).length() > 0 && generatedPersons.get(3).length() > 0);
    }


}
