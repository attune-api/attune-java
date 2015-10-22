package attune.client.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import attune.client.api.Anonymous;
import attune.client.model.Customer;

public class BindHystrixCommand extends HystrixCommand<Void> {
	private static final String GROUP_NAME = "attune-client";
	private final String anonymous;
	private final Customer request;
	private final String accessToken;
	private final Anonymous anon;

	public BindHystrixCommand(Anonymous anon, String anonymous, Customer request, String accessToken) {
		super(HystrixCommandGroupKey.Factory.asKey(GROUP_NAME));
		this.anon = anon;
		this.anonymous = anonymous;
		this.request = request;
		this.accessToken = accessToken;
	}
	@Override
	protected Void run() throws Exception {
		anon.update(anonymous, request, accessToken);
		return null;
	}

}
