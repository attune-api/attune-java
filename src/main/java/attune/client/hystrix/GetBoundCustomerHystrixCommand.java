package attune.client.hystrix;

import com.netflix.hystrix.HystrixCommandGroupKey;

import attune.client.ApiException;
import attune.client.HystrixConfig;
import attune.client.api.Anonymous;
import attune.client.model.Customer;

/**
 * Created by sudnya on 10/22/15.
 */
public class GetBoundCustomerHystrixCommand extends BaseApiHystrixCommand<Customer> {
    private final String anonymousId;
    private final String accessToken;
    private final Anonymous anon;

    public GetBoundCustomerHystrixCommand(Anonymous anon, String anonymousId, String accessToken, int numTries) {
    	super(HystrixCommandGroupKey.Factory.asKey(HystrixConfig.HYSTRIX_GROUP_NAME), accessToken, numTries);
		this.anon        = anon;
        this.anonymousId = anonymousId;
        this.accessToken = accessToken;
    }

    @Override
    protected Customer run() throws Exception {
    	return runWithRetry(new ApiCommandTask<Customer>() {

			@Override
			public Customer exec() throws ApiException {
				return anon.get(anonymousId, accessToken);
			}
		});
    }

}
