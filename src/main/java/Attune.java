import attune.client.AttuneClient;
import attune.client.MockClient;
import attune.client.RankingClient;

/**
 * Created by sudnya on 5/27/15.
 */
public class Attune {

    private boolean testMode = false;
    private RankingClient attuneClient;

    // singleton instance
    public Attune(boolean testMode) {
        if (!testMode) {
            attuneClient = AttuneClient.getInstance();
        } else {
            attuneClient = new MockClient();
        }
    }

    public RankingClient getAttuneClient() {
        return attuneClient;
    }


}
