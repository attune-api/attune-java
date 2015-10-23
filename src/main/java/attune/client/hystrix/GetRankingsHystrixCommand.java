package attune.client.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.exception.HystrixRuntimeException;

import attune.client.AttuneClient;
import attune.client.HystrixConfig;
import attune.client.api.Entities;
import attune.client.model.RankedEntities;
import attune.client.model.RankingParams;

public class GetRankingsHystrixCommand extends HystrixCommand<RankedEntities> {

	private final Entities entities;
	private final RankingParams params;
	private final String accessToken;

	public GetRankingsHystrixCommand(Entities entities, RankingParams params, String accessToken) {
		super(HystrixCommandGroupKey.Factory.asKey(HystrixConfig.HYSTRIX_GROUP_NAME));
		this.entities = entities;
		this.params = params;
		this.accessToken = accessToken;
	}

	@Override
	protected RankedEntities run() throws Exception {
		int counter = 0;
		RankedEntities retVal;

        while (true) {
            try {
            	retVal = entities.getRankings(params, accessToken);
                break;
            } catch (HystrixRuntimeException ex) {
                ++counter;
                if (counter > AttuneClient.MAX_RETRIES) {
                    throw ex;
                }
            }
        }
        return retVal;
	}

	@Override
	protected RankedEntities getFallback() {
		return returnDefaultRankings(this.params);
	}

	private RankedEntities returnDefaultRankings(RankingParams rankingParams) {
        RankedEntities rankedEntities = new RankedEntities();
        rankedEntities.setRanking(rankingParams.getIds());
        return rankedEntities;
    }
}
