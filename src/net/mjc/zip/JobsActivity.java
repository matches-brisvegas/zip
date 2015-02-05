package net.mjc.zip;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import net.mjc.zip.domain.IdCheck;
import net.mjc.zip.domain.Person;

public class JobsActivity extends Activity implements LoginDialog.Listener, LoginTask.Listener {

    private boolean loggedIn;
    private Person[] ppl;
    private Person[] pplDone;
    private LoginDialog dialog;


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

        ppl = new Person[3];

        ppl[0] = new Person();
        ppl[0].setFirstName("Joe");
        ppl[0].setLastName("Bloggs");
        ppl[0].setSex(Person.Sex.Male);
        ppl[0].setMatterId(5464233);
        ppl[0].setIdCheck(IdCheck.getIdCheck("NSW-01")); // TODO

        ppl[1] = new Person();
        ppl[1].setFirstName("Michelle");
        ppl[1].setLastName("Jones");
        ppl[1].setSex(Person.Sex.Female);
        ppl[1].setMatterId(5632981);
        ppl[1].setIdCheck(IdCheck.getIdCheck("NSW-01")); // TODO

        ppl[2] = new Person();
        ppl[2].setFirstName("Karen");
        ppl[2].setLastName("Smith");
        ppl[2].setSex(Person.Sex.Female);
        ppl[2].setMatterId(8743754);
        ppl[2].setIdCheck(IdCheck.getIdCheck("NSW-01")); // TODO

        pplDone = new Person[3];
        pplDone[0] = new Person();
        pplDone[0].setFirstName("Ricky");
        pplDone[0].setLastName("Bobby");
        pplDone[0].setSex(Person.Sex.Male);
        pplDone[0].setMatterId(2316490);
        pplDone[0].setIdCheck(IdCheck.getIdCheck("NSW-01")); // TODO
        pplDone[0].setIdCheckComplete(true);

        pplDone[1] = new Person();
        pplDone[1].setFirstName("Elizabeth");
        pplDone[1].setLastName("Knowles");
        pplDone[1].setSex(Person.Sex.Female);
        pplDone[1].setMatterId(3276490);
        pplDone[1].setIdCheck(IdCheck.getIdCheck("NSW-01")); // TODO
        pplDone[1].setIdCheckComplete(true);

        pplDone[2] = new Person();
        pplDone[2].setFirstName("Charles");
        pplDone[2].setLastName("Miller");
        pplDone[2].setSex(Person.Sex.Male);
        pplDone[2].setMatterId(5516490);
        pplDone[2].setIdCheck(IdCheck.getIdCheck("NSW-01")); // TODO
        pplDone[2].setIdCheckComplete(true);

//        login();

        dialog = new LoginDialog(this);

        ListView listAwaiting = (ListView) findViewById(R.id.listAwaitingView);
        listAwaiting.setAdapter(new PersonArrayAdapter(this, ppl));

        ListView listDone = (ListView) findViewById(R.id.listCompletedView);
        listDone.setAdapter(new PersonArrayAdapter(this, pplDone));

        setListViewHeightBasedOnChildren(listAwaiting);
        setListViewHeightBasedOnChildren(listDone);
    }


    public void setListViewHeightBasedOnChildren(ListView listView) {

        // TODO
        // You can also add: ListAdapter listAdapter = listView.getAdapter();
        // to setListViewHeightBasedOnChildren in order to make the adapter generic

        ArrayAdapter listAdapter = (ArrayAdapter) listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }


    @Override
    protected void onStart() {
        super.onStart();
        login();
    }

    public void showAlertDialog() {
//        final LoginDialog dialog = new LoginDialog(this);
        dialog.setListener(this);
        dialog.show();

//        final Button button = (Button) dialog.findViewById(R.id.signinButton);
//        button.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                onSigninClick(dialog);
//                loggedIn = true;
//            }
//        });

    }

    private void login() {

        if (!isLoggedIn()) {
            showAlertDialog();
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

    @Override
    public void onSigninClick(LoginDialog dialog) {

        new LoginTask(this).execute();
    }

    @Override
    public void onComplete(String token, Exception ex) {
        if (ex == null) {
            this.setTitle(getText(R.string.jobs) + " - Logged in");
        }
        dialog.dismiss();
    }
}
