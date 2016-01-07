package attune.client;

import com.google.common.base.Preconditions;

/**
 * Created by sudnya on 5/27/15.
 */
public class AttuneConfigurable {
    private final String defaultEndpoint                       = "http://localhost";
    private final double readTimeout                    = 0.25;
    private final double connectionTimeout              = 0.50;
    private final int maxPossiblePoolingConnections     = 1000;
    private final int maxConnections                    = 200;
    private final boolean testMode                      = false;
    private final boolean fallBackToDefault             = false;
    private int retryCount                              = 1; // retry once by default
    private boolean enableCompression                   = true;

    private final InitConfig initConfig;
    private final RuntimeConfig runtimeConfig;

    public AttuneConfigurable() {
        this.initConfig     = new InitConfig(defaultEndpoint, maxPossiblePoolingConnections, maxConnections, readTimeout, connectionTimeout);
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

    public AttuneConfigurable(String endpoint, Integer maxPossiblePoolingConnections, Integer maxConnections) {
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
    void updateFallbackToDefaultMode(boolean fallBackToDefault) {
        this.runtimeConfig.setFallBackToDefault(fallBackToDefault);
    }

    public void setRetryCount(int i) {
    	Preconditions.checkArgument(i > -1 && i < 20);
    	this.retryCount = i;
    }

    public int getRetryCount() {
    	return this.retryCount;
    }

    public void setEnableCompression(boolean enable) {
    	this.enableCompression = enable;
    }

    public boolean isEnableCompression() {
		return this.enableCompression;
	}
}
