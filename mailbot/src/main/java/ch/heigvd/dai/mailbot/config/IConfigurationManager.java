package ch.heigvd.dai.mailbot.config;

import ch.heigvd.dai.mailbot.model.mail.Message;

public interface IConfigurationManager {
    String getSmtpServerAddress();
    int getSmtpPort();
    int getNumberOfGroup();
    String[] getVictims();
    Message[] getMessages();
}
