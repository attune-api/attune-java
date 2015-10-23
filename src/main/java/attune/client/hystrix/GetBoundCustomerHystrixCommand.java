package attune.client.hystrix;

import attune.client.HystrixConfig;
import attune.client.api.Anonymous;
import attune.client.model.Customer;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * Created by sudnya on 10/22/15.
 */
public class GetBoundCustomerHystrixCommand extends HystrixCommand<Customer> {
    private final String anonymousId;
    private final String accessToken;
    private final Anonymous anon;

    public GetBoundCustomerHystrixCommand(Anonymous anon, String anonymousId, String accessToken) {
    	super(HystrixCommandGroupKey.Factory.asKey(HystrixConfig.HYSTRIX_GROUP_NAME));
		this.anon        = anon;
        this.anonymousId = anonymousId;
        this.accessToken = accessToken;
    }

    @Override
    protected Customer run() throws Exception {
        return anon.get(anonymousId, accessToken);
    }

}
