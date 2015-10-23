package attune.client.hystrix;

import attune.client.ApiException;
import attune.client.AttuneClient;
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
    private final Anonymous anonymous;

    public CreateAnonymousHystrixCommand(Anonymous anonymous, String accessToken) {
    	super(HystrixCommandGroupKey.Factory.asKey(HystrixConfig.HYSTRIX_GROUP_NAME));
		this.anonymous   = anonymous;
        this.accessToken = accessToken;
    }

    @Override
    protected AnonymousResult run() throws Exception {
        int counter = 0;
        AnonymousResult retVal;

        while (true) {
            try {
                retVal = anonymous.create(accessToken);
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
