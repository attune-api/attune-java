package attune.client;

/**
 * Created by sudnya on 5/27/15.
 */
public class AttuneConfigurable {
    private String endpoint                       = "http://localhost";
    private double readTimeout                    = 0.25;
    private double connectionTimeout              = 0.50;
    private int maxPossiblePoolingConnections     = 1000;
    private int maxConnections                    = 200;
    private boolean testMode                      = false;
    private boolean fallBackToDefault             = false;

    private final InitConfig initConfig;
    private RuntimeConfig runtimeConfig;

    public AttuneConfigurable() {
        this.initConfig     = new InitConfig(endpoint, maxPossiblePoolingConnections, maxConnections);
        this.runtimeConfig  = new RuntimeConfig(readTimeout, connectionTimeout, testMode, fallBackToDefault);
    }

    public AttuneConfigurable(String endpoint) {
        this.initConfig     = new InitConfig(endpoint, maxPossiblePoolingConnections, maxConnections);
        this.runtimeConfig  = new RuntimeConfig(readTimeout, connectionTimeout, testMode, fallBackToDefault);
    }

    public AttuneConfigurable(String endpoint, Integer maxPossiblePoolingConnections, Integer maxConnections) {
        this.initConfig     = new InitConfig(endpoint, maxPossiblePoolingConnections, maxConnections);
        this.runtimeConfig  = new RuntimeConfig(readTimeout, connectionTimeout, testMode, fallBackToDefault);
    }

    public InitConfig getInitConfig() {
        return this.initConfig;
    }

    public String getEndpoint() {
        return this.initConfig.getEndpoint();
    }

    public int getMaxPossiblePoolingConnections() {
        return this.initConfig.getMaxPossiblePoolingConnections();
    }

    public int getMaxConnections() {
        return this.initConfig.getMaxConnections();
    }

    public double getReadTimeout() {
        return this.runtimeConfig.getReadTimeout();
    }

    /**
     * Overrides the default read timeout value (in seconds)
     * @author sudnya
     * @example
     * updateReadTimeout(0.25)
     * @param readTimeout (in seconds), cannot be less than or equal to 0.0
     */
    public void updateReadTimeout(double readTimeout) throws ApiException {
        if (readTimeout <= 0.0) {
            throw new ApiException(400, "Read timeout value has to be greater than 0.0 seconds");
        }
        this.runtimeConfig.setReadTimeout(readTimeout);
    }

    public double getConnectionTimeout() {
        return this.runtimeConfig.getConnectionTimeout();
    }

    /**
     * Overrides the connection timeout value (in seconds)
     * @author sudnya
     * @example
     * updateConnectionTimeout(0.50)
     * @param connectionTimeout (in seconds), cannot be less than or equal to 0.0
     */
    public void updateConnectionTimeout(double connectionTimeout) throws ApiException {
        if (connectionTimeout <= 0.0) {
            throw new ApiException(400, "Connection timeout value has to be greater than 0.0 seconds");
        }
        this.runtimeConfig.setConnectionTimeout(connectionTimeout);
    }

    public boolean isTestMode() {
        return runtimeConfig.isTestMode();
    }

    /**
     * Overrides the default value of the test mode
     * @author sudnya
     * @example
     * updateTestMode(true)
     * @param testMode
     */
    public void updateTestMode(boolean testMode) {
        this.runtimeConfig.setTestMode(testMode);
    }

    public boolean isFallBackToDefault() {
        return this.runtimeConfig.isFallBackToDefault();
    }

    /**
     * Overrides the default value of the fallBackToDefaultMode
     * @author sudnya
     * @example
     * updateFallBackToDefaultMode(true)
     * @param fallBackToDefault
     */
    public void updateFallbackToDefaultMode(boolean fallBackToDefault) {
        this.runtimeConfig.setFallBackToDefault(fallBackToDefault);
    }
}
