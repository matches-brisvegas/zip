package net.mjc.zip.domain;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Person implements Serializable {

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
//    TODO JSON/GSON
//    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
//        out.writeChars(firstName);
//        out.writeChars(lastName);
//        out.writeChars(sex.toString());
//        out.writeBoolean(idCheckComplete);
//        out.writeLong(matterId);
//        out.writeInt(idChecks.size());
//        for (IdCheck check : idChecks) {
//            out.writeObject(check);
//        }
//    }
//
//    TODO JSON/GSON
//    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
//        firstName = in.readUTF();
//        lastName = in.readUTF();
//        sex = Sex.valueOf(in.readUTF());
//        idCheckComplete = in.readBoolean();
//        matterId = in.readLong();
//        int sz = in.readInt();
//        idChecks = new ArrayList<IdCheck>(sz);
//        for (int i = 0; i < sz; i++) {
//            idChecks.add((IdCheck) in.readObject());
//        }
//    }
}
