package ch.heigvd.dai.mailbot.model.mail;

public class Message {
    private String from;
    private String[] to = new String[0];
    private String[] cc = new String[0];
    private String[] bcc = new String[0]; // Cacher les personnes en copies
    private String subject;
    private String body;

    public Message() {}
    public Message (String msg) {
        // Peut-être msg.indexOf("\r\n\r\n") au lieu du gros truc ?
        subject = msg.substring(0, msg.indexOf(System.getProperty("line.separator")));
        body = msg.substring(msg.indexOf(System.getProperty("line.separator")) + 4);
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String[] getTo() {
        return to;
    }

    public void setTo(String[] to) {
        this.to = to;
    }

    public String[] getCc() {
        return cc;
    }

    public void setCc(String[] cc) {
        this.cc = cc;
    }

    public String[] getBcc() {
        return bcc;
    }

    public void setBcc(String[] bcc) {
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
