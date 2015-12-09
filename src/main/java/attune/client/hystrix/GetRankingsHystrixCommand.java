package attune.client.hystrix;

import attune.client.api.Entities;
import attune.client.model.RankedEntities;
import attune.client.model.RankingParams;

public class GetRankingsHystrixCommand extends GetRankingsGETHystrixCommand {

	private final RankingParams params;

	public GetRankingsHystrixCommand(Entities entities, RankingParams params, String accessToken, int numTries) {
		super(entities, params, accessToken, numTries);
		this.params = params;
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
