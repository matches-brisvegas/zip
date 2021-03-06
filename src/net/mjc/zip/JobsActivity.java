package net.mjc.zip;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import net.mjc.zip.domain.IdCheck;
import net.mjc.zip.domain.Person;
import net.mjc.zip.task.LoginTask;

import java.io.Serializable;

public class JobsActivity extends Activity implements LoginDialog.Listener, LoginTask.Listener, IdChecksRequiredDialog.Listener {

    private LoginDialog loginDialog;
    private IdChecksRequiredDialog idChecksRequiredDialog;
    private ActivityState state;

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
        state = new ActivityState();

        final ListView listAwaiting = (ListView) findViewById(R.id.listAwaitingView);
        listAwaiting.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final PersonArrayAdapter adapter = (PersonArrayAdapter) listAwaiting.getAdapter();
                final Person person = adapter.getItem(position);
                state.setPerson(person);
                state.beginIdChecks();
                showIdChecksRequiredDialog();
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

        if (requestCode == IdCheck.DRIVER_LICENSE && resultCode == RESULT_OK) {
//            Bitmap photo = (Bitmap) data.getExtras().get("data");     //TODO
//            Utils.saveBitmap(getFilesDir(), state, photo);  // TODO EXTRA_OUTPUT
        }
        handleNextActivity(state.getNextIdCheck());
//        if (next != null) {
//            Intent intent = next.createIntent(JobsActivity.this);
//            if (intent != null) {
//                intent.putExtra(ActivityState.class.getFullName(), ActivityState.toJson(state));
//                startActivityForResult(intent, next.getCode());
//            }
//        }
    }

    @Override
    protected void onSaveInstanceState(Bundle b) {
        super.onSaveInstanceState(b);
        getIntent().putExtra(ActivityState.class.getName(), ActivityState.toJson(state));
    }

    @Override
    protected void onRestoreInstanceState(Bundle b) {
        super.onRestoreInstanceState(b);
        state = ActivityState.fromJson(getIntent().getStringExtra(ActivityState.class.getName()));
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
    }

    public void showLoginDialog() {
        loginDialog.setListener(this);
        loginDialog.show();
    }

    public void showIdChecksRequiredDialog() {

        final Person person = state.getPerson();
        final StringBuilder text = new StringBuilder();

        text.append("The following Identification Checks are required :\n\n");

        for (IdCheck check : person.getIdChecks()) {
            text.append(" - ");
            text.append(check.getDescription());
            text.append("\n");
        }
        idChecksRequiredDialog = new IdChecksRequiredDialog(this, person.getFullName(), text.toString());
        idChecksRequiredDialog.setListener(this);
        idChecksRequiredDialog.show();
    }

    private void login() {
        if (!state.isLoggedIn()) {
            showLoginDialog();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        login();
    }

    @Override
    protected void onResume() {
        super.onResume();
        login();
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
        state.setLoggedIn(true);
        loginDialog.dismiss();
    }

    @Override
    public void onOkClick(IdChecksRequiredDialog dialog) {

        idChecksRequiredDialog.dismiss();
        handleNextActivity(state.getCurrentIdCheck());
    }

    private void handleNextActivity(final IdCheck next) {

        if (next == null) return;

        final Toast toast = next.getToast(JobsActivity.this);
        if (toast != null) {
            toast.show();
        }

        final AlertDialog alertDialog = next.getAlertDialog(JobsActivity.this);

        if (alertDialog == null) {
            startIntent(next);
        } else {
            alertDialog.setTitle(state.getPerson().getFullName());
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    startIntent(next);
                }
            });
            alertDialog.show();
        }
    }

    private void startIntent(final IdCheck next) {
        Intent intent = next.createIntent(JobsActivity.this);
        if (intent != null) {
            intent.putExtra(ActivityState.class.getName(), ActivityState.toJson(state));
            startActivityForResult(intent, next.getCode());
        }

    }
}
