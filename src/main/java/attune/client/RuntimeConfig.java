package attune.client;

/**
 * Created by sudnya on 8/25/15.
 */
public class RuntimeConfig {
    private Double readTimeout;
    private Double connectionTimeout;
    private boolean testMode;
    private boolean fallBackToDefault;

    public RuntimeConfig(Double readTimeout, Double connectionTimeout, boolean testMode, boolean fallBackToDefault) {
        this.readTimeout         = readTimeout;
        this.connectionTimeout   = connectionTimeout;
        this.testMode            = testMode;
        this.fallBackToDefault   = fallBackToDefault;
    }

    public Double getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(Double readTimeout) {
        this.readTimeout = readTimeout;
    }

    public Double getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(Double connectionTimeout) {
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

