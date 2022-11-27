package ch.heigvd.dai.mailbot.model.mail;

public class Person {
    private final String firstName;
    private final String lastName;
    private final Email address;

    public Person (String firstName, String lastName, Email address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Email getAddress() {
        return address;
    }
}
