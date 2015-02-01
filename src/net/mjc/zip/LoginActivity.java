package net.mjc.zip;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import net.mjc.zip.domain.IdCheck;
import net.mjc.zip.domain.Person;

public class LoginActivity extends Activity {

    private boolean loggedIn;
    private Person[] ppl;

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ppl = new Person[1];
        ppl[0] = new Person();
        ppl[0].setFirstName("Joe");
        ppl[0].setLastName("Bloggs");
        ppl[0].setSex(Person.Sex.Male);
        ppl[0].setIdCheck(IdCheck.getIdCheck("NSW-01")); // TODO

        ListView list = (ListView) findViewById(R.id.listAwaitingView);
        list.setAdapter(new PersonArrayAdapter(this, ppl));

        setContentView(R.layout.main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        login();
    }

    private void login() {

        if (!isLoggedIn()) {

        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
