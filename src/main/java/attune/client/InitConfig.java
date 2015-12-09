package attune.client;

import java.net.MalformedURLException;
import java.net.URL;

import jersey.repackaged.com.google.common.base.Preconditions;

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
    	Preconditions.checkArgument(isValidUrl(endpoint), "endpoint not a valid url [%s]", endpoint);
    	Preconditions.checkArgument(maxPossiblePoolingConnections > 0, "maxPossiblePoolingConnections cannot be negative [%s]", maxPossiblePoolingConnections);
    	Preconditions.checkArgument(maxConnections > 0, "maxConnections cannot be negative [%s]", maxConnections);
    	Preconditions.checkArgument(readTimeout > 0, "readTimeout cannot be negative [%s]", readTimeout);
    	Preconditions.checkArgument(connectionTimeout > 0, "connectionTimeout cannot be negative [%s]", connectionTimeout);

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

    private boolean isValidUrl(String u) {
    	boolean valid;
    	try {
			new URL(u);
			valid = true;
		} catch (MalformedURLException e) {
			valid = false;
		}
    	return valid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InitConfig)) return false;

        InitConfig that = (InitConfig) o;

        if (Double.compare(that.connectionTimeout, connectionTimeout) != 0) return false;
        if (maxConnections != that.maxConnections) return false;
        if (maxPossiblePoolingConnections != that.maxPossiblePoolingConnections) return false;
        if (Double.compare(that.readTimeout, readTimeout) != 0) return false;
        if (!endpoint.equals(that.endpoint)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = endpoint.hashCode();
        result = 31 * result + maxPossiblePoolingConnections;
        result = 31 * result + maxConnections;
        temp = Double.doubleToLongBits(readTimeout);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(connectionTimeout);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
