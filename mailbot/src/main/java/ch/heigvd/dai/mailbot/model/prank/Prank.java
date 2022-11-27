package ch.heigvd.dai.mailbot.model.prank;

import ch.heigvd.dai.mailbot.model.mail.Email;
import ch.heigvd.dai.mailbot.model.mail.Message;
import ch.heigvd.dai.mailbot.model.mail.Person;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Prank {
    private Person victimSender;
    private final List<Person> victimRecipients = new ArrayList<>();
    private final List<Person> witnessRecipients = new ArrayList<>();
    private Message message;



    public void addVictimRecipients(List<Person> victims) {
        victimRecipients.addAll(victims);
    }

    public void addWitnessRecipients(List<Person> witness) {
        witnessRecipients.addAll(witness);
    }

    public Person getVictimSender() {
        return victimSender;
    }

    public void setVictimSender(Person victimSender) {
        this.victimSender = victimSender;
    }

    public List<Person> getVictimRecipients() {
        return victimRecipients;
    }

    public List<Person> getWitnessRecipients() {
        return witnessRecipients;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Message generateMailMessage() {
        Message msg = new Message();

        msg.setBody(this.message.getBody() + "\r\n" + victimSender.getFirstName());

        Email[] to = victimRecipients
                .stream()
                .map(Person::getAddress)
                .collect(Collectors.toList())
                .toArray(new Email[]{});
        msg.setTo(to);

        msg.setFrom(victimSender.getAddress());
        msg.setSubject(this.message.getSubject());

        return msg;
    }
}
