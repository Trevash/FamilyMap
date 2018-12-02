package cs240.trevash.familymap.shared.Services;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.UUID;

import cs240.trevash.familymap.shared.Models.Event;
import cs240.trevash.familymap.shared.Models.Person;
import cs240.trevash.familymap.shared.Objects.EventPersonList;
import cs240.trevash.familymap.shared.Objects.FNames;
import cs240.trevash.familymap.shared.Objects.RegisterRequest;
import cs240.trevash.familymap.shared.Objects.SNames;

/**
 * Created by trevash on 3/5/18.
 */

public class generatePerson {

    private int genNum = 0;
    private generateEvent eventGenerator = new generateEvent();
    private JSONSampleReader jsonSampleReader = new JSONSampleReader();

    private int numberPersonsForGenerations(int generations) {
        int totalPersons = 1;
        int j = 1;

        for (int i = 1; i < generations + 1; i++) {
            j = (j * 2);
            totalPersons = totalPersons + j;
        }

        return totalPersons;
    }

    private String generateGender() {
        if (genNum == 0) {
            genNum = 1;
            return "M";
        }
        else {
            genNum = 0;
            return "F";
        }
    }

    public EventPersonList generatePersonsAndEventsForUser(int generations, RegisterRequest request) throws Exception {

        EventPersonList eventPersonList = new EventPersonList();
        int numPersons = numberPersonsForGenerations(generations);
        String father = null;
        String mother = null;
        String spouse = null;
        Event Marriage = null;

        FNames firstNames = jsonSampleReader.getFirstNames();
        SNames surnames = jsonSampleReader.getSurnames();

        int j = numPersons / 2;
        Queue<String> parentQueue = new LinkedList<>();
        Queue<String> spouseQueue = new LinkedList<>();

        for (int i = numPersons; i > 1; i--) {
            String personID = UUID.randomUUID().toString();

            if (i % 2 != 0) {
                spouseQueue.add(personID);
            }
            else {
                spouse = spouseQueue.remove();
                Marriage = eventGenerator.generateMarriage(request.getUserName(), spouse, personID);
                eventPersonList.addEvent(Marriage);
            }

            if (i <= j) {
                father = parentQueue.remove();
                mother = parentQueue.remove();
                Event Birth = eventGenerator.generateBirth(request.getUserName(), Marriage, personID);
                Event baptism = eventGenerator.generateBaptism(request.getUserName(), Marriage, personID);
                eventPersonList.addEvent(Birth);
                eventPersonList.addEvent(baptism);

            }

            Event Death = eventGenerator.generateDeath(request.getUserName(), Marriage, personID);
            eventPersonList.addEvent(Death);

            parentQueue.add(personID);
            Person person = new Person(personID, request.getUserName(), firstNames.chooseRandomName(), surnames.chooseRandomName(), generateGender());

            if (father != null)  person.setFatherId(father);
            if (mother != null)  person.setMotherId(mother);
            if (spouse != null)  person.setSpouseId(spouse);

            eventPersonList.addPerson(person);
        }

        String userPersonId = UUID.randomUUID().toString();
        eventPersonList.setUserPersonId(userPersonId);

        if (request.getFirstName() != null) {
            Person person = new Person(userPersonId, request.getUserName(), request.getFirstName(), request.getLastName(), request.getGender(), parentQueue.remove(), parentQueue.remove());
            eventPersonList.addPerson(person);
        }
        return eventPersonList;
    }

    private String genNoRelation(Person person) {
        StringBuilder sb = new StringBuilder();

        sb.append("('");
        sb.append(person.getPersonId());
        sb.append("','");
        sb.append(person.getDescendantUsername());
        sb.append("','");
        sb.append(person.getFirstName());
        sb.append("','");
        sb.append(person.getLastName());
        sb.append("','");
        sb.append(person.getGender());
        sb.append("'");
        sb.append("),");

        return sb.toString();
    }

    private String genParentRelation(Person person) {
        StringBuilder sb = new StringBuilder();

        sb.append("('");
        sb.append(person.getPersonId());
        sb.append("','");
        sb.append(person.getDescendantUsername());
        sb.append("','");
        sb.append(person.getFirstName());
        sb.append("','");
        sb.append(person.getLastName());
        sb.append("','");
        sb.append(person.getGender());
        sb.append("','");
        sb.append(person.getFatherId());
        sb.append("','");
        sb.append(person.getMotherId());
        sb.append("'");
        sb.append("),");

        return sb.toString();
    }

    private String genSpouseRelation(Person person) {
        StringBuilder sb = new StringBuilder();

        sb.append("('");
        sb.append(person.getPersonId());
        sb.append("','");
        sb.append(person.getDescendantUsername());
        sb.append("','");
        sb.append(person.getFirstName());
        sb.append("','");
        sb.append(person.getLastName());
        sb.append("','");
        sb.append(person.getGender());
        sb.append("','");
        sb.append(person.getSpouseId());
        sb.append("'");
        sb.append("),");

        return sb.toString();
    }


    public ArrayList<String> generateForSQL(ArrayList<Person> persons) {
        ArrayList<String> insertStrings = new ArrayList<>();

        StringBuilder fullPerson = new StringBuilder();
        StringBuilder noRelation = new StringBuilder();
        StringBuilder spouseRelation = new StringBuilder();
        StringBuilder parentRelation = new StringBuilder();

        for (int i = 0; i < persons.size(); i++) {
            if (persons.get(i).getFatherId() == null && persons.get(i).getMotherId() == null && persons.get(i).getSpouseId() == null) {
                noRelation.append(genNoRelation(persons.get(i)));
            }
            else if (persons.get(i).getFatherId() == null && persons.get(i).getMotherId() == null) {
                parentRelation.append(genSpouseRelation(persons.get(i)));
            }
            else if (persons.get(i).getSpouseId() == null) {
                spouseRelation.append(genParentRelation(persons.get(i)));
            }
            else {
                fullPerson.append("('");
                fullPerson.append(persons.get(i).getPersonId());
                fullPerson.append("','");
                fullPerson.append(persons.get(i).getDescendantUsername());
                fullPerson.append("','");
                fullPerson.append(persons.get(i).getFirstName());
                fullPerson.append("','");
                fullPerson.append(persons.get(i).getLastName());
                fullPerson.append("','");
                fullPerson.append(persons.get(i).getGender());
                fullPerson.append("','");
                fullPerson.append(persons.get(i).getFatherId());
                fullPerson.append("','");
                fullPerson.append(persons.get(i).getMotherId());
                fullPerson.append("','");
                fullPerson.append(persons.get(i).getSpouseId());
                fullPerson.append("'");
                fullPerson.append("),");
            }
        }

        if (fullPerson.length() > 0) fullPerson.setLength(fullPerson.length() - 1);
        if (noRelation.length() > 0) noRelation.setLength(noRelation.length() - 1);
        if (spouseRelation.length() > 0) spouseRelation.setLength(spouseRelation.length() - 1);
        if (parentRelation.length() > 0) parentRelation.setLength(parentRelation.length() - 1);

        String fullPersonString = fullPerson.toString();
        String noRelationString = noRelation.toString();
        String spouseRelationString = spouseRelation.toString();
        String parentRelationString = parentRelation.toString();

        insertStrings.add(fullPersonString);
        insertStrings.add(noRelationString);
        insertStrings.add(spouseRelationString);
        insertStrings.add(parentRelationString);

        return insertStrings;
    }
}
