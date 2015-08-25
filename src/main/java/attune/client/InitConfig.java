package attune.client;

/**
 * Created by sudnya on 8/25/15.
 */
public class InitConfig {
    private final String endpoint;
    private final Integer maxPossiblePoolingConnections;
    private final Integer maxConnections;

    public InitConfig(String endpoint, Integer maxPossiblePoolingConnections, Integer maxConnections) {
        this.endpoint                      = endpoint;
        this.maxPossiblePoolingConnections = maxPossiblePoolingConnections;
        this.maxConnections                = maxConnections;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public Integer getMaxPossiblePoolingConnections() {
        return maxPossiblePoolingConnections;
    }

    public Integer getMaxConnections() {
        return maxConnections;
    }
}
