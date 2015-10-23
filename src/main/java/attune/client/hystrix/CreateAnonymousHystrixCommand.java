package attune.client.hystrix;

import attune.client.api.Anonymous;
import attune.client.model.AnonymousResult;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * Created by sudnya on 10/22/15.
 */
public class CreateAnonymousHystrixCommand extends HystrixCommand<AnonymousResult> {
    private static final String GROUP_NAME = "attune-client";
    private final String accessToken;
    private final Anonymous anon;

    public CreateAnonymousHystrixCommand(Anonymous anon, String accessToken) {
        super(HystrixCommandGroupKey.Factory.asKey(GROUP_NAME));
        this.anon        = anon;
        this.accessToken = accessToken;
    }

    @Override
    protected AnonymousResult run() throws Exception {
        return anon.create(accessToken);
    }

}
