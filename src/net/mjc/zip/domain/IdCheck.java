package net.mjc.zip.domain;

import net.mjc.zip.CaptureSignatureActivity;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;

public class IdCheck implements Serializable {

    public static final String CUSTOMER_SIG = "CUSTOMER-SIG";
    public static final String WITNESS_SIG = "WITNESS-SIG";

    private static HashMap<String, IdCheck> idChecks = new HashMap<String, IdCheck>();

    private String code;
    private String description;
    private String activityClass;

    static {
        IdCheck idCheck1 = new IdCheck();
        idCheck1.setCode(CUSTOMER_SIG);
        idCheck1.setDescription("Customer Signature");
        idCheck1.setActivityClass(CaptureSignatureActivity.class.getName());
        idChecks.put(idCheck1.getCode(), idCheck1);

        IdCheck idCheck2 = new IdCheck();
        idCheck2.setCode(WITNESS_SIG);
        idCheck2.setDescription("Witness Signature");
        idCheck2.setActivityClass(CaptureSignatureActivity.class.getName());
        idChecks.put(idCheck2.getCode(), idCheck2);
    }

    public void setActivityClass(String activityClass) {
        this.activityClass = activityClass;
    }

    public Class<?> getActivityClass() throws ClassNotFoundException {
        return Class.forName(activityClass);
    }

    public static IdCheck getIdCheck(String code) {
        return idChecks.get(code);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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
            idCheck.setCode(jsonObj.getString("code"));
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
}
