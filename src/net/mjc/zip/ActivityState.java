package net.mjc.zip;

import android.content.Context;
import android.content.Intent;
import net.mjc.zip.domain.IdCheck;
import net.mjc.zip.domain.Person;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class ActivityState implements Serializable {

    private Person person;
    private int current = -1;

    public ActivityState(Person person) {
        this.person = person;
    }

    public IdCheck getCurrentIdCheck() {
        return person.getIdChecks().get(current);
    }

    public Intent getNextActivity(Context context) throws ClassNotFoundException {
        this.current++;
        if (current >= person.getIdChecks().size()) {
            return null;
        }
        Intent intent = new Intent(context, getCurrentIdCheck().getActivityClass());
        intent.putExtra(ActivityState.class.getName(), toJson(this));
        return intent;
    }

    public static ActivityState fromJson(String json) {
        try {
            JSONObject jsonObj = new JSONObject(json);

            Person person = Person.fromJson((String) jsonObj.get("person"));
            ActivityState state = new ActivityState(person);
            state.current = jsonObj.getInt("current");
            return state;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String toJson(ActivityState state) {
        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("person", Person.toJson(state.person));
            jsonObj.put("current", state.current);
            return jsonObj.toString();

        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}