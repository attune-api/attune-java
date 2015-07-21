package attune.client;

import org.slf4j.Logger;

/**
 * Created by sudnya on 5/27/15.
 */
public class AttuneConfigurable {
    protected String clientId = null;
    protected String clientSecret = null;
    protected String endpoint = "http://localhost";
    protected double timeout = 5.0;
    protected Logger logger;
    protected boolean testMode = false;

    public AttuneConfigurable(String endpoint, double timeout, String clientId, String clientSecret) {
        this.endpoint = endpoint;
        this.timeout  = timeout;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }
    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public boolean isTestMode() {
        return testMode;
    }

    public void setTestMode(boolean testMode) {
        this.testMode = testMode;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public double getTimeout() {
        return timeout;
    }

    public void setTimeout(double timeout) {
        this.timeout = timeout;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
}

