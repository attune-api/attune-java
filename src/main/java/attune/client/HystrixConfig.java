package attune.client;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

/**
 * Hystrix configuration properties.
 * @author jthomas
 *
 */
public class HystrixConfig {

	private static final String HYSTRIX_GROUP_NAME = "attune-client";
	private final Map<String, Object> params;

	private HystrixConfig(Map<String, Object> params) {
		this.params = params;
	}

	/**
	 * Returns an immutable view of the config params.
	 * @return
	 */
	public Map<String, Object> getParams() {
		return params;//(null != params)? params : ImmutableMap.of();
	}

	public static final class Builder {
		private final Map<String, Object> configParams;
		final String propertyNameStub = "hystrix.command."+HYSTRIX_GROUP_NAME;

		public Builder() {
			configParams = Maps.newHashMap();
			configParams.put(propertyNameStub + ".execution.isolation.strategy", "THREAD");
			configParams.put(propertyNameStub + ".execution.isolation.thread.timeoutInMilliseconds", 1000);
			configParams.put(propertyNameStub + ".fallback.enabled", Boolean.TRUE);
			configParams.put("hystrix.threadpool." + HYSTRIX_GROUP_NAME + ".coreSize", 10);
			configParams.put(propertyNameStub + ".circuitBreaker.enabled", Boolean.TRUE);
			configParams.put(propertyNameStub + ".circuitBreaker.errorThresholdPercentage", 50);
			configParams.put(propertyNameStub + ".circuitBreaker.requestVolumeThreshold", 20);
			configParams.put(propertyNameStub + ".circuitBreaker.sleepWindowInMilliseconds", 5000);
		}

		/**
		 * Perform fallback if a command fails.
		 * @return
		 */
		public Builder enableFallback() {
			configParams.put(propertyNameStub + ".fallback.enabled", Boolean.TRUE);
			return this;
		}

		/**
		 * No fallback if a command fails.
		 * @return
		 */
		public Builder disableFallback() {
			configParams.put(propertyNameStub + ".fallback.enabled", Boolean.FALSE);
			return this;
		}

		/**
		 * Number of threads in the hystrix thread pool.  If the pool is full calls are rejected
		 * and fallback is executed if enabled.
		 * @param size
		 * @return
		 */
		public Builder threadPoolSize(int size) {
			configParams.put(propertyNameStub + ".coreSize", size);
			return this;	
		}

		public Builder withTimeoutInMilliseconds(int timeout) {
			configParams.put(propertyNameStub + ".execution.isolation.thread.timeoutInMilliseconds", timeout);
			return this;
		}

		/**
		 * 
		 * @return
		 */
		public HystrixConfig build() {
			return new HystrixConfig(ImmutableMap.copyOf(configParams));
		}
	}


}
