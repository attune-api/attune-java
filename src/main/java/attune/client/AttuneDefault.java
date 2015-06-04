package attune.client;

import java.io.IOException;
import java.util.Properties;


/**
 * Created by sudnya on 5/27/15.
 */

public class AttuneDefault extends AttuneConfigurable {

    public AttuneDefault() {
        String fileName       = "config.properties";
        Properties configFile = new Properties();
        try {
            configFile.load(this.getClass().getClassLoader().getResourceAsStream(fileName));
            this.endpoint         = configFile.getProperty("end_point");
            this.timeout          = Long.parseLong(configFile.getProperty("timeout"));
            this.clientId         = configFile.getProperty("client_id");
            this.clientSecret     = configFile.getProperty("client_secret");
            this.logging_enabled  = Boolean.parseBoolean(configFile.getProperty("logging_enabled"));
            this.test_mode        = Boolean.parseBoolean(configFile.getProperty("test_mode"));
            this.retries          = Integer.parseInt(configFile.getProperty("retries"));
        } catch (IOException e) {
            logger.error("Error loading parameters from config file");
            e.printStackTrace();
        }
    }
}
