package attune.client;

/**
 * Created by sudnya on 5/27/15.
 */
public class AttuneConfigurable {
    private String endpoint                       = "http://localhost";
    private Double readTimeout                    = 0.25;
    private Double connectionTimeout              = 0.50;
    private Integer maxPossiblePoolingConnections = 1000;
    private Integer maxConnections                = 200;
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

    public Integer getMaxPossiblePoolingConnections() {
        return this.initConfig.getMaxPossiblePoolingConnections();
    }

    public Integer getMaxConnections() {
        return this.initConfig.getMaxConnections();
    }

    public Double getReadTimeout() {
        return this.runtimeConfig.getReadTimeout();
    }

    public void updateReadTimeout(Double readTimeout) {
        this.runtimeConfig.setReadTimeout(readTimeout);
    }

    public Double getConnectionTimeout() {
        return this.runtimeConfig.getConnectionTimeout();
    }

    public void updateConnectionTimeout(Double connectionTimeout) {
        this.runtimeConfig.setConnectionTimeout(connectionTimeout);
    }

    public boolean isTestMode() {
        return runtimeConfig.isTestMode();
    }

    public void updateTestMode(boolean testMode) {
        this.runtimeConfig.setTestMode(testMode);
    }

    public boolean isFallBackToDefault() {
        return this.runtimeConfig.isFallBackToDefault();
    }

    public void updateFallbackToDefaultMode(boolean fallBackToDefault) {
        this.runtimeConfig.setFallBackToDefault(fallBackToDefault);
    }


}
/*
    //Java does not support default params, so http://stackoverflow.com/questions/7428039/java-constructor-method-with-optional-parameters
    private AttuneConfigurable(ConfigBuilder configBuilder) {

        this.endpoint                      = configBuilder.endpoint;
        this.readTimeout                   = configBuilder.readTimeout;
        this.connectionTimeout             = configBuilder.connectionTimeout;
        this.maxPossiblePoolingConnections = configBuilder.maxPossiblePoolingConnections;
        this.maxConnections                = configBuilder.maxConnections;
        this.testMode                      = configBuilder.testMode;
        this.fallBackToDefault             = configBuilder.fallBackToDefault;
    }

    public static class ConfigBuilder {
        private String endpoint = "http://localhost";
        private Double readTimeout = 0.25;
        private Double connectionTimeout = 0.50;
        private int maxPossiblePoolingConnections = 1000;
        private int maxConnections = 200;
        private boolean testMode = false;
        private boolean fallBackToDefault = false;

        public ConfigBuilder endpoint(String endpoint) {
            this.endpoint = endpoint;
            return this;
        }

        public ConfigBuilder readTimeout(Double readTimeout) {
            this.readTimeout = readTimeout;
            return this;
        }

        public ConfigBuilder connectionTimeout(Double connectionTimeout) {
            this.connectionTimeout = connectionTimeout;
            return this;
        }

        public ConfigBuilder maxPossiblePoolingConnections(int maxPossiblePoolingConnections) {
            this.maxPossiblePoolingConnections = maxPossiblePoolingConnections;
            return this;
        }

        public ConfigBuilder maxConnections(int maxConnections) {
            this.maxConnections = maxConnections;
            return this;
        }

        public ConfigBuilder testMode(boolean testMode) {
            this.testMode = testMode;
            return this;
        }

        public ConfigBuilder fallBackToDefault(boolean fallBackToDefault) {
            this.fallBackToDefault = fallBackToDefault;
            return this;
        }

        public AttuneConfigurable build() {
            return new AttuneConfigurable(this);
        }
    }

    public boolean isFallBackToDefault() {
        return fallBackToDefault;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public Double getReadTimeout() {
        return readTimeout;
    }

    public Double getConnectionTimeout() {
        return connectionTimeout;
    }

    public int getMaxPossiblePoolingConnections() {
        return maxPossiblePoolingConnections;
    }

    public int getMaxConnections() {
        return maxConnections;
    }

    public boolean isTestMode() {
        return testMode;
    }
}*/
