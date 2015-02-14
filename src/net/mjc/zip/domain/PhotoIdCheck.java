package net.mjc.zip.domain;

import android.content.Context;
import android.content.Intent;

public class PhotoIdCheck extends IdCheck {

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
