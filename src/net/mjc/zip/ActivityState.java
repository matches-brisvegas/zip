package net.mjc.zip;

import net.mjc.zip.domain.IdCheck;
import net.mjc.zip.domain.Person;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class ActivityState implements Serializable {

    private Person person;
    private int current = -1;
    private boolean loggedIn;

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }

    public IdCheck getCurrentIdCheck() {
        return person.getIdChecks().get(current);
    }

    public IdCheck getNextIdCheck() {
        this.current++;
        if (current >= person.getIdChecks().size()) {
            return null;
        }
        return getCurrentIdCheck();
    }

//    public Intent createIntent(Context context) throws ClassNotFoundException {
//        final Intent intent = getCurrentIdCheck().createIntent(context);
//        new Intent(context, getCurrentIdCheck().getActivityClass());
//        intent.putExtra(ActivityState.class.getFullName(), toJson(this));
//        return intent;
//    }

    public static ActivityState fromJson(String json) {
        try {
            JSONObject jsonObj = new JSONObject(json);

            ActivityState state = new ActivityState();
            state.person = Person.fromJson((String) jsonObj.get("person"));
            state.current = jsonObj.getInt("current");
            state.loggedIn = jsonObj.getBoolean("loggedIn");
            return state;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String toJson(ActivityState state) {
        try {
            JSONObject jsonObj = new JSONObject();
            if (state != null) {
                jsonObj.put("person", Person.toJson(state.person));
                jsonObj.put("current", state.current);
                jsonObj.put("loggedIn", state.loggedIn);
            }
            return jsonObj.toString();

        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public IdCheck beginIdChecks() {
        this.current = -1;
        return getNextIdCheck();
    }
}