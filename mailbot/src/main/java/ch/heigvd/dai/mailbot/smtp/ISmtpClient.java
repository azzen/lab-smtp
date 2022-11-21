package ch.heigvd.dai.mailbot.smtp;

import ch.heigvd.dai.mailbot.model.mail.Message;

import java.io.IOException;

public interface ISmtpClient {
    void sendMessage(Message message) throws IOException;
}
