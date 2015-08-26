package attune.client;

/**
 * Created by sudnya on 8/25/15.
 */
public class RuntimeConfig {
    private double readTimeout;
    private double connectionTimeout;
    private boolean testMode;
    private boolean fallBackToDefault;

    public RuntimeConfig(double readTimeout, double connectionTimeout, boolean testMode, boolean fallBackToDefault) {
        this.readTimeout         = readTimeout;
        this.connectionTimeout   = connectionTimeout;
        this.testMode            = testMode;
        this.fallBackToDefault   = fallBackToDefault;
    }

    public double getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(double readTimeout) {
        this.readTimeout = readTimeout;
    }

    public double getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(double connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public boolean isTestMode() {
        return testMode;
    }

    public void setTestMode(boolean testMode) {
        this.testMode = testMode;
    }

    public boolean isFallBackToDefault() {
        return fallBackToDefault;
    }

    public void setFallBackToDefault(boolean fallBackToDefault) {
        this.fallBackToDefault = fallBackToDefault;
    }
}

