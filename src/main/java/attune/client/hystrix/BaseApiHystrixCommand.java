package attune.client.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import attune.client.ApiException;

/**
 * Base class for hystrix commands used in the client.
 * @author jthomas
 *
 * @param <R> Return type
 */
public abstract class BaseApiHystrixCommand<R> extends HystrixCommand<R> {

	private int numTries;

	/**
	 * Constructor.
	 * @param group
	 * @param accessToken
	 * @param numTries total number of tries
	 */
	public BaseApiHystrixCommand(HystrixCommandGroupKey group, String accessToken, int numTries) {
		super(group);
		this.numTries = numTries;
	}

	/**
	 * Execute the actual command while applying the retry strategy.
	 * @param t
	 * @return
	 * @throws ApiException
	 */
	protected R runWithRetry(ApiCommandTask<R> t) throws ApiException {
		int counter = 0;
		R retVal;

        while (true) {
            try {
            	retVal = t.exec();
                break;
            } catch (ApiException ex) {
            	++counter;
                if (counter >= numTries || !ex.isRetriable()) {
                    throw ex;
                }
            }
        }
        return retVal;
	}

	/**
	 * Functional interface for actual command executed by the hystrix command wrapper.
	 * @author jthomas
	 *
	 * @param <R>
	 * @FunctionalInterface
	 */
	interface ApiCommandTask<R> {
		/**
		 * Execute the actual command.
		 * @return
		 * @throws ApiException
		 */
		R exec() throws ApiException;
	}
}
