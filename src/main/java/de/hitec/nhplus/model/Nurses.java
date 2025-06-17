package de.hitec.nhplus.model;

public class Nurses extends Person {
    private String nurseId;
    private String department;

    public Nurses(String name, String firstName, String nurseId, String department) {
        super(name, firstName);
        this.nurseId = nurseId;
        this.department = department;
    }

    public String getNurseId() {
        return nurseId;
    }

    public void setNurseId(String nurseId) {
        this.nurseId = nurseId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
