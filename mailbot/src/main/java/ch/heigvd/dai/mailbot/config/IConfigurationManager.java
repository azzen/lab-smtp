package ch.heigvd.dai.mailbot.config;

import ch.heigvd.dai.mailbot.model.mail.Message;
import ch.heigvd.dai.mailbot.model.mail.Person;

public interface IConfigurationManager {
    String getSmtpServerAddress();
    int getSmtpPort();
    int getNumberOfGroup();
    Person[] getVictims();
    Message[] getMessages();
}
