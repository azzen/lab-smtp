package ch.heigvd.dai.mailbot.model.mail;

public class Person {
    private final String fname;
    private final String lname;
    private final String address;

    Person (String fname, String lname, String address) {
        this.fname = fname;
        this.lname = lname;
        this.address = address;
    }

    // TODO Autre constructeur ?

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getAddress() {
        return address;
    }
}
