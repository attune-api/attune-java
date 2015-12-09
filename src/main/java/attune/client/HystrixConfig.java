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

	public static final String HYSTRIX_GROUP_NAME = "attune-client";
	private final Map<String, Object> params;
	static final String propertyNameStub = "hystrix.command.";// + HYSTRIX_GROUP_NAME;
	public static final String PROPERTY_NAME_HYSTRIX_FALLBACK_ENABLE = propertyNameStub + ".fallback.enabled";
	static final String[] HYSTRIX_COMMANDS = {"BindHystrixCommand", "CreateAnonymousHystrixCommand", 
			"GetBoundCustomerHystrixCommand", "GetRankingsHystrixCommand", "GetRankingsGETHystrixCommand"};

	private HystrixConfig(Map<String, Object> params) {
		this.params = params;
	}

	/**
	 * Returns an immutable view of the config params.
	 * @return
	 */
	public Map<String, Object> getParams() {
		return params;
	}

	public static final class Builder {
		private final Map<String, Object> configParams;

		public Builder() {
			configParams = Maps.newHashMap();
			for (String cmdKey:HYSTRIX_COMMANDS) {
				String cmdPropertyNameStub = propertyNameStub + cmdKey;
				configParams.put(cmdPropertyNameStub + ".execution.isolation.strategy", "THREAD");
				configParams.put(cmdPropertyNameStub + ".execution.isolation.thread.timeoutInMilliseconds", 100000);
				configParams.put(cmdPropertyNameStub + ".fallback.enabled", Boolean.TRUE);
				configParams.put(cmdPropertyNameStub + ".circuitBreaker.enabled", Boolean.TRUE);
				configParams.put(cmdPropertyNameStub + ".circuitBreaker.errorThresholdPercentage", 50);
				configParams.put(cmdPropertyNameStub + ".circuitBreaker.requestVolumeThreshold", 20);
				configParams.put(cmdPropertyNameStub + ".circuitBreaker.sleepWindowInMilliseconds", 5000);
			}
			configParams.put("hystrix.threadpool." + HYSTRIX_GROUP_NAME + ".coreSize", 10);
		}

		/**
		 * Perform fallback if a command fails.
		 * @return
		 */
		public Builder enableFallback() {
			for (String cmdKey:HYSTRIX_COMMANDS) {
				String cmdPropertyNameStub = propertyNameStub + cmdKey;
				configParams.put(cmdPropertyNameStub + ".fallback.enabled", Boolean.TRUE);
			}
			return this;
		}

		/**
		 * No fallback if a command fails.
		 * @return
		 */
		public Builder disableFallback() {
			for (String cmdKey:HYSTRIX_COMMANDS) {
				String cmdPropertyNameStub = propertyNameStub + cmdKey;
				configParams.put(cmdPropertyNameStub + ".fallback.enabled", Boolean.FALSE);
			}
			return this;
		}

		/**
		 * Number of threads in the hystrix thread pool.  If the pool is full calls are rejected
		 * and fallback is executed if enabled.
		 * @param size
		 * @return
		 */
		public Builder threadPoolSize(int size) {
			configParams.put("hystrix.threadpool." + HYSTRIX_GROUP_NAME + ".coreSize", 10);
			return this;	
		}

		public Builder withTimeoutInMilliseconds(int timeout) {
			for(String cmdKey:HYSTRIX_COMMANDS) {
				String cmdPropertyNameStub = propertyNameStub + cmdKey;
				configParams.put(cmdPropertyNameStub + ".execution.isolation.thread.timeoutInMilliseconds", timeout);
			}
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
