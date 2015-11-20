package attune.client;

/**
 * Created by sudnya on 5/27/15.
 */
public class Attune {

    private RankingClient attuneClient;

    // singleton instance
    public Attune(boolean testMode) {
        if (!testMode) {
            attuneClient = AttuneClient.buildWith(new AttuneConfigurable());
        } else {
            attuneClient = new MockClient();
        }
    }

    public RankingClient getAttuneClient() {
        return attuneClient;
    }


}
