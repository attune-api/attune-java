package attune.client.hystrix;

import com.netflix.hystrix.HystrixCommandGroupKey;

import attune.client.ApiException;
import attune.client.HystrixConfig;
import attune.client.api.Anonymous;
import attune.client.model.AnonymousResult;

/**
 * Created by sudnya on 10/22/15.
 */
public class CreateAnonymousHystrixCommand extends BaseApiHystrixCommand<AnonymousResult> {
    private final String accessToken;
    private final Anonymous anon;

    public CreateAnonymousHystrixCommand(Anonymous anon, String accessToken, int numTries) {
    	super(HystrixCommandGroupKey.Factory.asKey(HystrixConfig.HYSTRIX_GROUP_NAME), accessToken, numTries);
		this.anon        = anon;
        this.accessToken = accessToken;
    }

    @Override
    protected AnonymousResult run() throws Exception {
    	return runWithRetry(new ApiCommandTask<AnonymousResult>() {

			@Override
			public AnonymousResult exec() throws ApiException {
				return anon.create(accessToken);
			}
		});
    }

}
