package net.mjc.zip;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import net.mjc.zip.domain.IdCheck;
import net.mjc.zip.domain.Person;

public class LoginActivity extends Activity {

    private boolean loggedIn;
    private Person[] ppl;
    private Person[] pplDone;

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

        setContentView(R.layout.main);

        ppl = new Person[1];

        ppl[0] = new Person();
        ppl[0].setFirstName("Joe");
        ppl[0].setLastName("Bloggs");
        ppl[0].setSex(Person.Sex.Male);
        ppl[0].setMatterId(5464233);
        ppl[0].setIdCheck(IdCheck.getIdCheck("NSW-01")); // TODO

//        ppl[1] = new Person();
//        ppl[1].setFirstName("Karen");
//        ppl[1].setLastName("Smith");
//        ppl[1].setSex(Person.Sex.Female);
//        ppl[1].setMatterId(8743754);
//        ppl[1].setIdCheck(IdCheck.getIdCheck("NSW-01")); // TODO

        pplDone = new Person[1];
        pplDone[0] = new Person();
        pplDone[0].setFirstName("Ricky");
        pplDone[0].setLastName("Bobby");
        pplDone[0].setSex(Person.Sex.Male);
        pplDone[0].setMatterId(2316490);
        pplDone[0].setIdCheck(IdCheck.getIdCheck("NSW-01")); // TODO
        pplDone[0].setIdCheckComplete(true);

        ListView list = (ListView) findViewById(R.id.listAwaitingView);
        list.setAdapter(new PersonArrayAdapter(this, ppl));

        ListView listDone = (ListView) findViewById(R.id.listCompletedView);
        listDone.setAdapter(new PersonArrayAdapter(this, pplDone));

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
