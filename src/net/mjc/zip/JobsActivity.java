package net.mjc.zip;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import net.mjc.zip.domain.Person;
import net.mjc.zip.task.LoginTask;

import java.io.Serializable;

public class JobsActivity extends Activity implements LoginDialog.Listener, LoginTask.Listener {

    private boolean loggedIn;
    private LoginDialog loginDialog;

    private ActivityState state;

    public boolean isLoggedIn() {
        return loggedIn;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.jobs);

        if (savedInstanceState != null) {
            Serializable s = savedInstanceState.getSerializable(null);
            if (s instanceof ActivityState) {
                state = (ActivityState) s;
            }
        }

        Person[] ppl = Person.createAwaiting();
        Person[] pplDone = Person.createDone();

        loginDialog = new LoginDialog(this);

        final ListView listAwaiting = (ListView) findViewById(R.id.listAwaitingView);
        listAwaiting.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final PersonArrayAdapter adapter = (PersonArrayAdapter)listAwaiting.getAdapter();
                final Person person = adapter.getItem(position);
                state = new ActivityState(person);
                try {
                    startActivityForResult(state.getNextActivity(JobsActivity.this), 100);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        listAwaiting.setAdapter(new PersonArrayAdapter(this, ppl));

        ListView listDone = (ListView) findViewById(R.id.listCompletedView);
        listDone.setAdapter(new PersonArrayAdapter(this, pplDone));

        setListViewHeightBasedOnChildren(listAwaiting);
        setListViewHeightBasedOnChildren(listDone);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            Intent i = state.getNextActivity(JobsActivity.this);
            if (i != null) {
                startActivityForResult(i, 100);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    public void setListViewHeightBasedOnChildren(ListView listView) {

        // TODO
        // You can also add: ListAdapter listAdapter = listView.getAdapter();
        // to setListViewHeightBasedOnChildren in order to make the adapter generic

        ArrayAdapter listAdapter = (ArrayAdapter) listView.getAdapter();
        if (listAdapter == null) {
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

    public void showLoginDialog() {
        loginDialog.setListener(this);
        loginDialog.show();
    }

    private void login() {

        if (!isLoggedIn()) {
            showLoginDialog();
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
    public void onLoginComplete(String token, Exception ex) {
        if (ex == null) {
            this.setTitle(getText(R.string.jobs) + " - Logged in");
        }
        loggedIn = true;
        loginDialog.dismiss();
    }
}
