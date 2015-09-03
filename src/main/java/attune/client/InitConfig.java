package attune.client;

/**
 * Created by sudnya on 8/25/15.
 */
public class InitConfig {
    private final String endpoint;
    private final int maxPossiblePoolingConnections;
    private final int maxConnections;
    private double readTimeout;
    private double connectionTimeout;

    public InitConfig(String endpoint, int maxPossiblePoolingConnections, int maxConnections, double readTimeout, double connectionTimeout) {
        this.endpoint                      = endpoint;
        this.maxPossiblePoolingConnections = maxPossiblePoolingConnections;
        this.maxConnections                = maxConnections;
        this.readTimeout                   = readTimeout;
        this.connectionTimeout             = connectionTimeout;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public int getMaxPossiblePoolingConnections() {
        return maxPossiblePoolingConnections;
    }

    public int getMaxConnections() {
        return maxConnections;
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

}
