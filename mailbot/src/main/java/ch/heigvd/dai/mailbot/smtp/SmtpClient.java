package ch.heigvd.dai.mailbot.smtp;

import ch.heigvd.dai.mailbot.config.IConfigurationManager;
import ch.heigvd.dai.mailbot.model.mail.Email;
import ch.heigvd.dai.mailbot.model.mail.Message;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class SmtpClient implements ISmtpClient {
    private static final Logger LOG = Logger.getLogger(SmtpClient.class.getName());
    IConfigurationManager config;
    private String smtpServerAddress;
    private int smtpServerPort;
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;

    public SmtpClient(String address, int port, IConfigurationManager config) {
        this.config = config;
        smtpServerAddress = address;
        smtpServerPort = port;
    }

    @Override
    public void sendMessage(Message message) throws IOException {
        Socket socket = new Socket(smtpServerAddress, smtpServerPort);
        writer = new PrintWriter(new OutputStreamWriter(
                socket.getOutputStream(), StandardCharsets.UTF_8));
        reader = new BufferedReader(new InputStreamReader(
                socket.getInputStream(), StandardCharsets.UTF_8));
        String line = reader.readLine();
        LOG.info(line);
        writer.write("EHLO " + smtpServerAddress + "\r\n");
        writer.flush();
        line = reader.readLine();
        LOG.info(line);


        if (!line.startsWith("250")) throw new IOException("SMTP Error: " + line);
        while (line.startsWith("250-")) {
            line = reader.readLine();
            LOG.info(line);
        }
        writer.write("MAIL FROM:");
        writer.write(message.getFrom().toString());
        writer.write("\r\n");
        writer.flush();
        line = reader.readLine();
        LOG.info(line);

        for (Email to : message.getTo()) {
            writer.write("RCPT TO:");
            writer.write(to.toString());
            writer.write("\r\n");
            writer.flush();
            line = reader.readLine();
            LOG.info(line);

        }

        for (Email to : message.getCc()) {
            writer.write("RCPT TO:");
            writer.write(to.toString());
            writer.write("\r\n");
            writer.flush();
            line = reader.readLine();
            LOG.info(line);

        }

        for (Email to : message.getBcc()) {
            writer.write("RCPT TO:");
            writer.write(to.toString());
            writer.write("\r\n");
            writer.flush();
            line = reader.readLine();
            LOG.info(line);

        }

        writer.write("DATA\r\n");
        writer.flush();
        line = reader.readLine();
        LOG.info(line);

        writer.write("Content-type: text/plain; charset=\"utf-8\"\r\n");
        String encodedSubject = Base64.getEncoder().encodeToString(message.getSubject().getBytes());
        writer.write("Subject: =?utf8?B?" + encodedSubject + "?=\r\n");
        writer.write("From: " + message.getFrom() + "\r\n");
        
        String to = Arrays.stream(message.getTo()).map(Email::toString)
                .collect(Collectors.joining(", "));
        
        writer.write("To: " + to + "\r\n");

        String cc = Arrays.stream(message.getCc()).map(Email::toString)
                .collect(Collectors.joining(", "));

        writer.write("Cc: " + cc + "\r\n");

        writer.flush();

        writer.write(message.getBody());

        writer.write("\r\n");
        writer.write(".");
        writer.write("\r\n");

        writer.flush();

        line = reader.readLine();
        writer.write("QUIT\r\n");
        writer.flush();

        reader.close();
        writer.close();
        socket.close();
    }
}
