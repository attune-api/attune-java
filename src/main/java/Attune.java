import io.swagger.attune.AttuneClient;
import io.swagger.attune.MockClient;
import io.swagger.attune.RankingClient;

/**
 * Created by sudnya on 5/27/15.
 */
public class Attune {

    private boolean testMode = false;
    private RankingClient attuneClient;

    public Attune(boolean testMode) {
        if (!testMode) {
            attuneClient = new AttuneClient();
        } else {
            attuneClient = new MockClient();
        }
    }

    public RankingClient getAttuneClient() {
        return attuneClient;
    }


}
