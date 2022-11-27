package ch.heigvd.dai.mailbot.model.prank;

import ch.heigvd.dai.mailbot.config.IConfigurationManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import ch.heigvd.dai.mailbot.model.mail.Person;
import ch.heigvd.dai.mailbot.model.mail.Message;
import ch.heigvd.dai.mailbot.model.mail.Group;

public class PrankGenerator {
    private final static int MIN_VICTIMS = 3;

    private final static Logger LOG = Logger.getLogger(PrankGenerator.class.getName());

    private IConfigurationManager configurationManager;

    public PrankGenerator(IConfigurationManager configurationManager) {
        this.configurationManager = configurationManager;
    }


    public List<Prank> generatePranks() throws Exception {
        List<Prank> pranks = new ArrayList<>();
        Message[] messages = configurationManager.getMessages();

        int messageIndex = 0;
        int groupCount = configurationManager.getNumberOfGroup();
        int victimCount = configurationManager.getVictims().length;

        if (victimCount / groupCount < MIN_VICTIMS) {
            groupCount = victimCount / MIN_VICTIMS;
        }

        /*
        List<Group> groups = generateGroups(configurationManager.getVictims(), groupCount);

        for (Group group : groups) {
            Prank prank = new Prank();
            List<Person> victims = group.getMembers();
            Collections.shuffle(victims);
            Person sender = victims.remove(0);
            prank.setVictimSender(sender);
            prank.addVictimRecipients(victims);
            // prank.addWitnessRecipients(configurationManager.getWitnessToCC());

            Message message = messageIndex < messages.length ? messages[messageIndex] : messages[0];
            messageIndex = (messageIndex + 1) % messages.length;
            prank.setMessage(message);
            pranks.add(prank);
        }

         */
        return pranks;
    }


    /*
    public List<Group> generateGroups(Person[] victims, int groupCount) {
        List<Person> availableVictims = new ArrayList(victims);
        Collections.shuffle(availableVictims);
        List<Group> groups = new ArrayList<>();
        for (int i = 0; i < groupCount; ++i) {
            Group group = new Group();
            groups.add(group);
        }

        int turn = 0;
        Group targetGroup;
        while (availableVictims.size() > 0) {
            targetGroup = groups.get(turn);
            turn = (turn + 1) % groups.size();
            Person victim = availableVictims.remove(0);
            targetGroup.addMember(victim);
        }
        return groups;
    }
    */

}
