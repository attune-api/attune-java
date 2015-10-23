package attune.client.hystrix;

import attune.client.ApiException;
import attune.client.AttuneClient;
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
    private final Anonymous anonymous;

    public GetBoundCustomerHystrixCommand(Anonymous anonymous, String anonymousId, String accessToken) {
    	super(HystrixCommandGroupKey.Factory.asKey(HystrixConfig.HYSTRIX_GROUP_NAME));
		this.anonymous   = anonymous;
        this.anonymousId = anonymousId;
        this.accessToken = accessToken;
    }

    @Override
    protected Customer run() throws Exception {
        int counter = 0;
        Customer retVal;
        while (true) {
            try {
                retVal = anonymous.get(anonymousId, accessToken);
                break;
            } catch (ApiException ex) {
                ++counter;
                if (counter > AttuneClient.MAX_RETRIES) {
                    throw ex;
                }
            }
        }
        return retVal;
    }

    //no fallback for this call
}
