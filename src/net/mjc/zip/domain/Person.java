package net.mjc.zip.domain;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Person implements Serializable {

    public static Person[] createAwaiting() {
        Person[] ppl = new Person[3];

        ppl[0] = new Person();
        ppl[0].setFirstName("Joe");
        ppl[0].setLastName("Bloggs");
        ppl[0].setSex(Person.Sex.Male);
        ppl[0].setMatterId(5464233);
        ppl[0].addIdCheck(IdCheck.getIdCheck(IdCheck.DRIVER_LICENSE));
        ppl[0].addIdCheck(IdCheck.getIdCheck(IdCheck.CUSTOMER_SIG));
        ppl[0].addIdCheck(IdCheck.getIdCheck(IdCheck.WITNESS_SIG));

        ppl[1] = new Person();
        ppl[1].setFirstName("Michelle");
        ppl[1].setLastName("Jones");
        ppl[1].setSex(Person.Sex.Female);
        ppl[1].setMatterId(5632981);
        ppl[1].addIdCheck(IdCheck.getIdCheck(IdCheck.PASSPORT));
        ppl[1].addIdCheck(IdCheck.getIdCheck(IdCheck.CUSTOMER_SIG));
        ppl[1].addIdCheck(IdCheck.getIdCheck(IdCheck.WITNESS_SIG));

        ppl[2] = new Person();
        ppl[2].setFirstName("Karen");
        ppl[2].setLastName("Smith");
        ppl[2].setSex(Person.Sex.Female);
        ppl[2].setMatterId(8743754);
        ppl[2].addIdCheck(IdCheck.getIdCheck(IdCheck.CUSTOMER_SIG));

        return ppl;
    }

    public static Person[] createDone() {

        Person[] pplDone = new Person[3];
        pplDone[0] = new Person();
        pplDone[0].setFirstName("Ricky");
        pplDone[0].setLastName("Bobby");
        pplDone[0].setSex(Person.Sex.Male);
        pplDone[0].setMatterId(2316490);
        pplDone[0].addIdCheck(IdCheck.getIdCheck(IdCheck.CUSTOMER_SIG));
        pplDone[0].setIdCheckComplete(true);

        pplDone[1] = new Person();
        pplDone[1].setFirstName("Elizabeth");
        pplDone[1].setLastName("Knowles");
        pplDone[1].setSex(Person.Sex.Female);
        pplDone[1].setMatterId(3276490);
        pplDone[1].addIdCheck(IdCheck.getIdCheck(IdCheck.CUSTOMER_SIG));
        pplDone[1].setIdCheckComplete(true);

        pplDone[2] = new Person();
        pplDone[2].setFirstName("Charles");
        pplDone[2].setLastName("Miller");
        pplDone[2].setSex(Person.Sex.Male);
        pplDone[2].setMatterId(5516490);
        pplDone[2].addIdCheck(IdCheck.getIdCheck(IdCheck.CUSTOMER_SIG));
        pplDone[2].setIdCheckComplete(true);

        return pplDone;
    }

    public enum Sex { Male, Female }

    private String firstName;
    private String lastName;
    private Sex sex;
    private long matterId;
    private boolean idCheckComplete;

    private ArrayList<IdCheck> idChecks = new ArrayList<IdCheck>();

    public long getMatterId() {
        return matterId;
    }

    public void setMatterId(long matterId) {
        this.matterId = matterId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public ArrayList<IdCheck> getIdChecks() {
        return idChecks;
    }

    public void addIdCheck(IdCheck idCheck) {
        this.idChecks.add(idCheck);
    }

    public void setIdCheckComplete(boolean idCheckComplete) {
        this.idCheckComplete = idCheckComplete;
    }

    public boolean isIdCheckComplete() {
        return idCheckComplete;
    }

    public static Person fromJson(String json) {
        try {
            Person person = new Person();
            JSONObject jsonObj = new JSONObject(json);
            person.setFirstName(jsonObj.getString("firstName"));
            person.setLastName(jsonObj.getString("lastName"));
            person.setSex(Sex.valueOf(jsonObj.getString("sex")));
            person.setMatterId(jsonObj.getLong("matterId"));
            person.setIdCheckComplete(jsonObj.getBoolean("idCheckComplete"));

            JSONArray jsonIdChecks = jsonObj.getJSONArray("idChecks");

            for (int i = 0; i < jsonIdChecks.length(); i++) {
                IdCheck idCheck = IdCheck.fromJson((String)jsonIdChecks.get(i));
                person.addIdCheck(idCheck);
            }
            return person;
        }
        catch(JSONException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String toJson(Person person) {
        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("firstName", person.getFirstName());
            jsonObj.put("lastName", person.getLastName());
            jsonObj.put("sex", person.getSex().toString());
            jsonObj.put("matterId", person.getMatterId());
            jsonObj.put("idCheckComplete", person.isIdCheckComplete());

            JSONArray jsonIdChecks = new JSONArray();

            for (IdCheck check : person.getIdChecks()) {
                jsonIdChecks.put(IdCheck.toJson(check));
            }
            jsonObj.put("idChecks", jsonIdChecks);
            return jsonObj.toString();
        }
        catch(JSONException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
