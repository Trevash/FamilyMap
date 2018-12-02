package cs240.trevash.familymap.shared.Services;

import org.junit.Test;

import java.util.ArrayList;

import cs240.trevash.familymap.shared.Models.User;

import static org.junit.Assert.assertTrue;

/**
 * Created by trevash on 3/5/18.
 */

public class generateUserTest {
    private GenerateUser generateUser = new GenerateUser();

    @Test
    public void generateEventForSQLTest() {
        ArrayList<User> users = new ArrayList<>();
        User user = new User("pId", "uName", "pw", "email@mail.com", "fName", "lName", "g");
        User user1 = new User("pId1", "uName1", "pw", "email@mail.com", "fName", "lName", "g");
        users.add(user);
        users.add(user1);

        String generatedUsers = generateUser.generateForSQL(users);
        assertTrue(generatedUsers.equals("('uName','pw','email@mail.com','fName','lName','g','pId'),('uName1','pw','email@mail.com','fName','lName','g','pId1')"));
    }
}
