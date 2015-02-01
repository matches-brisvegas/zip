package net.mjc.zip.domain;

/**
 * Created by matt on 1/02/2015.
 */
public class Person {

    public enum Sex { Male, Female }

    private String firstName;
    private String lastName;
    private Sex sex;

    private IdCheck idCheck;
    private boolean idCheckComplete;
    private long matterId;

    public long getMatterId() {
        return matterId;
    }

    public void setMatterId(long matterId) {
        this.matterId = matterId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public IdCheck getIdCheck() {
        return idCheck;
    }

    public void setIdCheck(IdCheck idCheck) {
        this.idCheck = idCheck;
    }

    public boolean isIdCheckComplete() {
        return idCheckComplete;
    }

    public void setIdCheckComplete(boolean idCheckComplete) {
        this.idCheckComplete = idCheckComplete;
    }
}
