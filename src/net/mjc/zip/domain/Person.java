package net.mjc.zip.domain;

/**
 * Created by matt on 1/02/2015.
 */
public class Person {

    public enum Sex { Male, Female }

    private String firstName;
    private String middleName;
    private String lastName;
    private Sex sex;

    private IdCheck idCheck;
    private boolean idCheckComplete;
    private long jobId;

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
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
