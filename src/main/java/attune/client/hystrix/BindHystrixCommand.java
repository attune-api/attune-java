package attune.client.hystrix;

import attune.client.ApiException;
import attune.client.AttuneClient;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import attune.client.HystrixConfig;
import attune.client.api.Anonymous;
import attune.client.model.Customer;

public class BindHystrixCommand extends HystrixCommand<Void> {

    private final String anonymousId;
	private final Customer customer;
	private final String accessToken;
	private final Anonymous anonymous;

	public BindHystrixCommand(Anonymous anonymous, String anonymousId, Customer customer, String accessToken) {
		super(HystrixCommandGroupKey.Factory.asKey(HystrixConfig.HYSTRIX_GROUP_NAME));
		this.anonymous   = anonymous;
		this.anonymousId = anonymousId;
		this.customer    = customer;
		this.accessToken = accessToken;
	}

	@Override
	protected Void run() throws Exception {
		int counter = 0;
		while (true) {
			try {
				anonymous.update(anonymousId, customer, accessToken);
				break;
			} catch (ApiException ex) {
				++counter;
				if (counter > AttuneClient.MAX_RETRIES) {
					throw ex;
				}
			}
		}
        return null;
	}

    //no fallback for this call
}
