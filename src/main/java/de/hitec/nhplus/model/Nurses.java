package de.hitec.nhplus.model;

public class Nurses {
    private long nid;
    private String surname;
    private String firstName;
    private String phone;

    public Nurses(long nid, String surname, String firstName, String phone) {
        this.nid = nid;
        this.surname = surname;
        this.firstName = firstName;
        this.phone = phone;
    }

    public Nurses(String surname, String firstName, long nid, String phone) {
        this(nid, surname, firstName, phone);
    }

    // Getter/Setter
    public long getNid() { return nid; }
    public void setNid(long nid) { this.nid = nid; }
    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}
