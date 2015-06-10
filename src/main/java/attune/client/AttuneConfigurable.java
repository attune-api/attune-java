package attune.client;

import org.slf4j.Logger;

/**
 * Created by sudnya on 5/27/15.
 */
public class AttuneConfigurable {
    protected String clientId         = null;
    protected String clientSecret     = null;
    protected String endpoint         = "http://localhost";
    protected long timeout            = 5;
    protected int retries             = 1;
    protected Logger logger;
    protected boolean logging_enabled = false;
    protected boolean test_mode       = false;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public boolean isTest_mode() {
        return test_mode;
    }

    public void setTest_mode(boolean test_mode) {
        this.test_mode = test_mode;
    }

    public boolean isLogging_enabled() {
        return logging_enabled;
    }

    public void setLogging_enabled(boolean logging_enabled) {
        this.logging_enabled = logging_enabled;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
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

    public int getRetries() {
        return retries;
    }

    public void setRetries(int retries) {
        this.retries = retries;
    }
}
