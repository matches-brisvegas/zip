package net.mjc.zip.domain;

import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.widget.Toast;
import net.mjc.zip.CameraActivity;
import net.mjc.zip.CaptureSignatureActivity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;

public class IdCheck implements Serializable {  //TODO

    public static final int CUSTOMER_SIG =100;
    public static final int WITNESS_SIG = 200;
    public static final int DRIVER_LICENSE = 300;
    public static final int PASSPORT = 400;

    private static HashMap<Integer, IdCheck> idChecks = new HashMap<Integer, IdCheck>();

    private int code;
    private String description;
    private String activityClass;
    private String actionText;

    static {
        IdCheck customerSig = new IdCheck();
        customerSig.setCode(CUSTOMER_SIG);
        customerSig.setDescription("Customer Signature");
        customerSig.setActivityClass(CaptureSignatureActivity.class.getName());
        idChecks.put(customerSig.getCode(), customerSig);

        IdCheck witnessSig = new IdCheck();
        witnessSig.setCode(WITNESS_SIG);
        witnessSig.setDescription("Witness Signature");
        witnessSig.setActivityClass(CaptureSignatureActivity.class.getName());
        idChecks.put(witnessSig.getCode(), witnessSig);

        IdCheck driverLicense = new PhotoIdCheck();
        driverLicense.setCode(DRIVER_LICENSE);
        driverLicense.setDescription("Driver's License");
        driverLicense.setActionText("Capture Driver's License");
        driverLicense.setActivityClass(MediaStore.ACTION_IMAGE_CAPTURE);
        idChecks.put(driverLicense.getCode(), driverLicense);

        IdCheck passport = new IdCheck();
        passport.setActivityClass(CameraActivity.class.getName());
        passport.setCode(PASSPORT);
        passport.setDescription("Passport");
        passport.setActionText("Capture Passport");
        idChecks.put(passport.getCode(), passport);
    }

    public String getActionText() {
        return actionText;
    }

    public void setActionText(String actionText) {
        this.actionText = actionText;
    }

    public Toast getToast(Context context) {
        return null;
    }

    public android.app.AlertDialog getAlertDialog(Context context) {
        return null;
    }

    public void setActivityClass(String activityClass) {
        this.activityClass = activityClass;
    }

    public String getActivityClass() throws ClassNotFoundException {
        return activityClass;
    }

    public static IdCheck getIdCheck(int code) {
        return idChecks.get(code);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static IdCheck fromJson(String json) {
        try {
            JSONObject jsonObj = new JSONObject(json);
            IdCheck idCheck = new IdCheck();
            idCheck.setCode(jsonObj.getInt("code"));
            idCheck.setDescription(jsonObj.getString("description"));
            idCheck.setActivityClass(jsonObj.getString("activityClass"));
            return idCheck;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String toJson(IdCheck check) {
        try {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("code", check.getCode());
            jsonObj.put("description", check.getDescription());
            jsonObj.put("activityClass", check.activityClass);
            return jsonObj.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Intent createIntent(Context context) {
        try {
            Intent intent = new Intent(context, Class.forName(getActivityClass()));
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                return intent;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
