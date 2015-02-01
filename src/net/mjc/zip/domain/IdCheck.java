package net.mjc.zip.domain;

import java.util.HashMap;

/**
 * Created by matt on 1/02/2015.
 */
public class IdCheck {

    private static HashMap<String, IdCheck> idChecks = new HashMap<String, IdCheck>();

    static {
        IdCheck idCheck1 = new IdCheck();
        idCheck1.setCode("NSW-01");  //TODO
        idCheck1.setDescription("NSW Property Check");
        idChecks.put(idCheck1.getCode(), idCheck1);
    }

    public static IdCheck getIdCheck(String code) {
        return idChecks.get(code);
    }

    private String code;
    private String description;

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
}
