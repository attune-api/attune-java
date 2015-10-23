package attune.client.hystrix;

import attune.client.HystrixConfig;
import attune.client.api.Anonymous;
import attune.client.model.AnonymousResult;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * Created by sudnya on 10/22/15.
 */
public class CreateAnonymousHystrixCommand extends HystrixCommand<AnonymousResult> {
    private final String accessToken;
    private final Anonymous anon;

    public CreateAnonymousHystrixCommand(Anonymous anon, String accessToken) {
    	super(HystrixCommandGroupKey.Factory.asKey(HystrixConfig.HYSTRIX_GROUP_NAME));
		this.anon        = anon;
        this.accessToken = accessToken;
    }

    @Override
    protected AnonymousResult run() throws Exception {
        return anon.create(accessToken);
    }

}
