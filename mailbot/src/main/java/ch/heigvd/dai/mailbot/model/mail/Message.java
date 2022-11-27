package ch.heigvd.dai.mailbot.model.mail;

public class Message {
    private Email from;
    private Email[] to = new Email[0];
    private Email[] cc = new Email[0];
    private Email[] bcc = new Email[0]; // Cacher les personnes en copies
    private String subject;
    private String body;

    public Message() {}
    public Message (String msg) {
        subject = msg.substring(0, msg.indexOf(System.getProperty("line.separator")));
        body = msg.substring(msg.indexOf(System.getProperty("line.separator")) +
                System.getProperty("line.separator").length() * 2);
    }

    public Email getFrom() {
        return from;
    }

    public void setFrom(Email from) {
        this.from = from;
    }

    public Email[] getTo() {
        return to;
    }

    public void setTo(Email[] to) {
        this.to = to;
    }

    public Email[] getCc() {
        return cc;
    }

    public void setCc(Email[] cc) {
        this.cc = cc;
    }

    public Email[] getBcc() {
        return bcc;
    }

    public void setBcc(Email[] bcc) {
        this.bcc = bcc;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
