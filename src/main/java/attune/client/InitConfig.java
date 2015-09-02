package attune.client;

/**
 * Created by sudnya on 8/25/15.
 */
public class InitConfig {
    private final String endpoint;
    private final int maxPossiblePoolingConnections;
    private final int maxConnections;

    public InitConfig(String endpoint, int maxPossiblePoolingConnections, int maxConnections) {
        this.endpoint                      = endpoint;
        this.maxPossiblePoolingConnections = maxPossiblePoolingConnections;
        this.maxConnections                = maxConnections;
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
}
