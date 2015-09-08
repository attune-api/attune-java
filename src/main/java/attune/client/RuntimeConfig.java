package attune.client;

/**
 * Created by sudnya on 8/25/15.
 */
public class RuntimeConfig {
    private boolean testMode;
    private boolean fallBackToDefault;

    public RuntimeConfig(boolean testMode, boolean fallBackToDefault) {
        this.testMode            = testMode;
        this.fallBackToDefault   = fallBackToDefault;
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

