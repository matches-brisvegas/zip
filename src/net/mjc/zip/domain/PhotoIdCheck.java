package net.mjc.zip.domain;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class PhotoIdCheck extends IdCheck {

    @Override
    public AlertDialog getAlertDialog(Context context) {
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(getActionText());
        builder.setCancelable(false);
        return builder.create();
    }

//    @Override
//    public Toast getToast(Context context) {
//        return Toast.makeText(context, getActionText(), Toast.LENGTH_LONG);
//    }

    @Override
    public Intent createIntent(Context context) {
        try {
            Intent intent = new Intent(getActivityClass());
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                return intent;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
