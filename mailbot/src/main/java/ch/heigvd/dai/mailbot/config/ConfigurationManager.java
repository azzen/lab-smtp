package ch.heigvd.dai.mailbot.config;

import ch.heigvd.dai.mailbot.model.mail.Message;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigurationManager implements IConfigurationManager {
    private static final Logger LOG = Logger.getLogger(ConfigurationManager.class.getName());
    private static ConfigurationManager instance = null;
    private String smtpServerAddress;
    private int smtpPort;
    private int numberOfGroups;
    private String[] victims;
    private Message[] messages;

    private ConfigurationManager() {
        try (InputStream in = Files.newInputStream(Path.of("config/mailbot.properties"))) {
            Properties prop = new Properties();
            prop.load(in);
            smtpServerAddress = prop.getProperty("smtpServerAddress");
            smtpPort = Integer.parseInt(prop.getProperty("smtpPort"));
            numberOfGroups = Integer.parseInt(prop.getProperty("numberOfGroups"));
            String messageFilename = prop.getProperty("messageFile");
            parseMessages(messageFilename);
            String victimsFilename = prop.getProperty("victimsFile");
            parseEmail(victimsFilename);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, "Configuration file not found, cannot start application");
            System.exit(1);
        }
    }
    public static ConfigurationManager getInstance() {
        if (instance == null) {
            instance = new ConfigurationManager();
        }
        return instance;
    }

    private void parseMessages(String filename) {
        try {
            String content = Files.readString(Paths.get(filename));
            String[] rawMessages = content.split("//--//\n");
            this.messages = new Message[rawMessages.length];
            for (int i = 0; i < rawMessages.length; ++i) {
                this.messages[i] = new Message(rawMessages[i]);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void parseEmail(String filename) {
        try {
            String content = Files.readString(Paths.get(filename));
            this.victims = content.split("\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getNumberOfGroup() {
        return getInstance().numberOfGroups;
    }

    @Override
    public int getSmtpPort() {
        return smtpPort;
    }

    @Override
    public String getSmtpServerAddress() {
        return getInstance().smtpServerAddress;
    }
    @Override
    public String[] getVictims() {
        return getInstance().victims;
    }

    @Override
    public Message[] getMessages() {
        return getInstance().messages;
    }

}
