package attune.client.hystrix;

import com.netflix.hystrix.HystrixCommandGroupKey;

import attune.client.ApiException;
import attune.client.HystrixConfig;
import attune.client.api.Entities;
import attune.client.model.RankedEntities;
import attune.client.model.RankingParams;

public class GetRankingsGETHystrixCommand extends BaseApiHystrixCommand<RankedEntities> {

	private final Entities entities;
	private final RankingParams params;
	private final String accessToken;

	public GetRankingsGETHystrixCommand(Entities entities, RankingParams params, String accessToken, int numTries) {
		super(HystrixCommandGroupKey.Factory.asKey(HystrixConfig.HYSTRIX_GROUP_NAME), accessToken, numTries);
		this.entities = entities;
		this.params = params;
		this.accessToken = accessToken;
	}

	@Override
	protected RankedEntities run() throws Exception {
		RankedEntities rankedEntities = 
		runWithRetry(new ApiCommandTask<RankedEntities>() {

			@Override
			public RankedEntities exec() throws ApiException {
				return entities.getRankings(params, accessToken);
			}
		});
		return rankedEntities;
	}

}
