package attune.client.hystrix;

import com.netflix.hystrix.HystrixCommandGroupKey;

import attune.client.ApiException;
import attune.client.HystrixConfig;
import attune.client.api.Anonymous;
import attune.client.model.Customer;

public class BindHystrixCommand extends BaseApiHystrixCommand<Void> {
	private final String anonymous;
	private final Customer request;
	private final String accessToken;
	private final Anonymous anon;

	public BindHystrixCommand(Anonymous anon, String anonymous, Customer request, String accessToken, int numTries) {
		super(HystrixCommandGroupKey.Factory.asKey(HystrixConfig.HYSTRIX_GROUP_NAME), accessToken, numTries);
		this.anon = anon;
		this.anonymous = anonymous;
		this.request = request;
		this.accessToken = accessToken;
	}

	@Override
	protected Void run() throws Exception {
		return runWithRetry(new ApiCommandTask<Void>() {

			@Override
			public Void exec() throws ApiException {
				anon.update(anonymous, request, accessToken);
				return null;
			}
		});
	}

}
