package cs240.trevash.familymap.shared.Objects;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by trevash on 3/6/18.
 */

/**
 * Generates an arrayList of first names for use when generating a user object.
 */
public class FNames {
    private ArrayList<String> FNames = new ArrayList<>();

    public FNames(ArrayList<String> FNames) {
        this.FNames = FNames;
    }

    public FNames() {
        FNames = new ArrayList<String>();
    }

    /**
     * Allows addition of strings to FNames arrayList
     * @param name - String name to be added to arrayList
     */
    public void add(String name) {
        FNames.add(name);
    }

    public ArrayList<String> getNames() {
        return FNames;
    }

    /**
     * Provides a random name from the FNames arrayList
     * @return - the random string name to be returned
     */
    public String chooseRandomName() {
        Random randomGenerator = new Random();
        int index = randomGenerator.nextInt(FNames.size());
        return FNames.get(index);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < FNames.size(); i++) {
            sb.append(FNames.get(i));
            sb.append("\n");
        }

        return sb.toString();
    }
}
