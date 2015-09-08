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
        this.initConfig     = new InitConfig(endpoint, maxPossiblePoolingConnections, maxConnections, readTimeout, connectionTimeout);
        this.runtimeConfig  = new RuntimeConfig(testMode, fallBackToDefault);
    }

    public AttuneConfigurable(String endpoint) {
        this.initConfig     = new InitConfig(endpoint, maxPossiblePoolingConnections, maxConnections, readTimeout, connectionTimeout);
        this.runtimeConfig  = new RuntimeConfig(testMode, fallBackToDefault);
    }

    public AttuneConfigurable(String endpoint, Integer maxPossiblePoolingConnections, Integer maxConnections, Double readTimeout, Double connectionTimeout) {
        this.initConfig     = new InitConfig(endpoint, maxPossiblePoolingConnections, maxConnections, readTimeout, connectionTimeout);
        this.runtimeConfig  = new RuntimeConfig(testMode, fallBackToDefault);
    }

    public AttuneConfigurable(String endpoint, Double readTimeout, Double connectionTimeout) {
        this.initConfig     = new InitConfig(endpoint, maxPossiblePoolingConnections, maxConnections, readTimeout, connectionTimeout);
        this.runtimeConfig  = new RuntimeConfig(testMode, fallBackToDefault);
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
        return this.initConfig.getReadTimeout();
    }

    public double getConnectionTimeout() {
        return this.initConfig.getConnectionTimeout();
    }

    public boolean isTestMode() {
        return runtimeConfig.isTestMode();
    }

    /**
     * Overrides the default value of the test mode
     * @author sudnya
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
     * @param fallBackToDefault
     */
    public void updateFallbackToDefaultMode(boolean fallBackToDefault) {
        this.runtimeConfig.setFallBackToDefault(fallBackToDefault);
    }
}
