package net.mjc.zip;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

public class LoginDialog extends AlertDialog {

    public interface Listener {
        public void onSigninClick(LoginDialog dialog);
    }

    Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void signin(View view) {
        listener.onSigninClick(LoginDialog.this);
    }

    public LoginDialog(Context context) {
        super(context);
        final LayoutInflater li = LayoutInflater.from(context);
        View content = li.inflate(R.layout.login, null);
        setView(content);


        final Button button = (Button) content.findViewById(R.id.signinButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                listener.onSigninClick(LoginDialog.this);
            }
        });

    }
}