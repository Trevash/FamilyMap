package cs240.trevash.familymap.shared.Services;

import java.util.ArrayList;

import cs240.trevash.familymap.shared.Models.User;

/**
 * Created by trevash on 3/5/18.
 */

public class GenerateUser {

    public String generateForSQL(ArrayList<User> users) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < users.size(); i++) {
            stringBuilder.append("('");
            stringBuilder.append(users.get(i).getUserName());
            stringBuilder.append("',");
            stringBuilder.append("'");
            stringBuilder.append(users.get(i).getPassword());
            stringBuilder.append("',");
            stringBuilder.append("'");
            stringBuilder.append(users.get(i).getEmailAddress());
            stringBuilder.append("',");
            stringBuilder.append("'");
            stringBuilder.append(users.get(i).getFirstName());
            stringBuilder.append("',");
            stringBuilder.append("'");
            stringBuilder.append(users.get(i).getLastName());
            stringBuilder.append("',");
            stringBuilder.append("'");
            stringBuilder.append(users.get(i).getGender());
            stringBuilder.append("',");
            stringBuilder.append("'");
            stringBuilder.append(users.get(i).getPersonId());
            stringBuilder.append("'),");
        }
        stringBuilder.setLength(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }
}
