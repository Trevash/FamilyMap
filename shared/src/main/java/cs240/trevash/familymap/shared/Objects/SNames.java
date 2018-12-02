package cs240.trevash.familymap.shared.Objects;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by trevash on 3/6/18.
 */

/**
 * Stores object of surnames in SNames arrayList.
 */
public class SNames {
    private ArrayList<String> SNames = new ArrayList<>();

    public SNames(ArrayList<String> SNames) {
        this.SNames = SNames;
    }

    public ArrayList<String> getNames() {
        return SNames;
    }

    /**
     * Chooses a random String surname from SNames arrayList
     * @return - random surName string
     */
    public String chooseRandomName() {
        Random randomGenerator = new Random();
        int index = randomGenerator.nextInt(SNames.size());
        return SNames.get(index);
    }

    /**
     * Adds name to SNames arrayList
     * @param name - string name
     */
    public void add(String name) {
        SNames.add(name);
    }
}
