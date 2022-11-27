package ch.heigvd.dai.mailbot.model.mail;

public class Email {
    private String email;

    public Email(String email) {
        if (this.validateEmail(email)) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Couldn't validate email address " + email);
        }
    }

    private boolean validateEmail(String email) {
        String pattern = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        return email.matches(pattern);
    }

    @Override
    public String toString() {
        return this.email;
    }

}
