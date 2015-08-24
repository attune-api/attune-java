package attune.client;

import org.slf4j.Logger;

/**
 * Created by sudnya on 5/27/15.
 */
public class AttuneConfigurable {
    protected String endpoint = "http://localhost";
    protected double timeout = 5.0;
    protected Logger logger;
    protected boolean testMode = false;
    protected boolean fallBackToDefault = false;

    public AttuneConfigurable(String endpoint, double timeout) {
        this.endpoint = endpoint;
        this.timeout  = timeout;
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

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public boolean isFallBackToDefault() {
        return fallBackToDefault;
    }

    public void setFallBackToDefault(boolean fallBackToDefault) {
        this.fallBackToDefault = fallBackToDefault;
    }
}

