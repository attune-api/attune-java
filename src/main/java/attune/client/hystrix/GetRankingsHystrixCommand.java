package attune.client.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import attune.client.api.Entities;
import attune.client.model.RankedEntities;
import attune.client.model.RankingParams;

public class GetRankingsHystrixCommand extends HystrixCommand<RankedEntities> {
	private static final String GROUP_NAME = "attune-client";

	private final Entities entities;
	private final RankingParams params;
	private final String accessToken;

	public GetRankingsHystrixCommand(Entities entities, RankingParams params, String accessToken) {
		super(HystrixCommandGroupKey.Factory.asKey(GROUP_NAME));
		this.entities = entities;
		this.params = params;
		this.accessToken = accessToken;
	}

	@Override
	protected RankedEntities run() throws Exception {
		return entities.getRankings(this.params, this.accessToken);
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
