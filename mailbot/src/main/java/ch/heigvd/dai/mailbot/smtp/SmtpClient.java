package ch.heigvd.dai.mailbot.smtp;

import ch.heigvd.dai.mailbot.config.IConfigurationManager;
import ch.heigvd.dai.mailbot.model.mail.Message;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

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
        writer.write(message.getFrom());
        writer.write("\r\n");
        writer.flush();
        line = reader.readLine();
        LOG.info(line);

        for (String to : message.getTo()) {
            writer.write("RCPT TO:");
            writer.write(to);
            writer.write("\r\n");
            writer.flush();
            line = reader.readLine();
            LOG.info(line);

        }

        for (String to : message.getTo()) {
            writer.write("RCPT TO:");
            writer.write(to);
            writer.write("\r\n");
            writer.flush();
            line = reader.readLine();
            LOG.info(line);

        }

        for (String to : message.getBcc()) {
            writer.write("RCPT TO:");
            writer.write(to);
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

        writer.write("Subject: " + message.getSubject() + "\r\n");
        writer.write("From: " + message.getFrom() + "\r\n");
        writer.write("To: " + String.join(", ", message.getTo()) + "\r\n");
        writer.write("Cc: " + String.join(", ", message.getCc()) + "\r\n");
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
