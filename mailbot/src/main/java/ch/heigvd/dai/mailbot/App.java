package ch.heigvd.dai.mailbot;

import ch.heigvd.dai.mailbot.config.ConfigurationManager;
import ch.heigvd.dai.mailbot.config.IConfigurationManager;
import ch.heigvd.dai.mailbot.model.prank.Prank;
import ch.heigvd.dai.mailbot.model.prank.PrankGenerator;
import ch.heigvd.dai.mailbot.smtp.ISmtpClient;
import ch.heigvd.dai.mailbot.smtp.SmtpClient;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class App
{
    private static final Logger LOG = Logger.getLogger(App.class.getName());
    public static void main( String[] args )
    {
        startPranksCampaign();
    }

    public static void startPranksCampaign() {
        IConfigurationManager config = ConfigurationManager.getInstance();
        List<Prank> pranks = null;

        try {
            pranks = (new PrankGenerator(config)).generatePranks();
        } catch (Exception ex) {
            LOG.severe(ex.getMessage());
            return;
        }

        ISmtpClient client = new SmtpClient(config.getSmtpServerAddress(), config.getSmtpPort(), config);

        LOG.info("Launching pranks!");

        for (Prank p : pranks) {
            try {
                client.sendMessage(p.generateMailMessage());
            } catch (IOException ex) {
                LOG.severe(ex.getMessage());
                return;
            } catch (RuntimeException ex) {
                LOG.severe(ex.getMessage());
            }
        }
    }
}
