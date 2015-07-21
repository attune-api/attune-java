import attune.client.AttuneClient;
import attune.client.AttuneConfigurable;
import attune.client.MockClient;
import attune.client.RankingClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Attune Tester.
 *
 * @author <sudnya>
 * @version 1.0
 * @since <pre>June 01, 2015</pre>
 */
public class AttuneTest {
    AttuneConfigurable config;

    @Before
    public void before() throws Exception {
        String endpoint = "http://api.attune-staging.co";
        double timeout = 5.0;
        String clientId = "attune";
        String clientSecret = "a433de60fe2311e3a3ac0800200c9a66";
        this.config = new AttuneConfigurable(endpoint, timeout, clientId, clientSecret);
    }

    @After
    public void after() throws Exception {
    }


    /**
     * Method: test MockClient is instantiated when testMode is true
     * @throws Exception
     */
    @Test
    public void testMockInstanceInTestMode() throws Exception {
        boolean isTestMode   = true;
        Attune attune        = new Attune(isTestMode, config);
        RankingClient client = attune.getAttuneClient();

        assertTrue(client instanceof MockClient);
    }

    /**
     * Method: test MockClient is instantiated when testMode is false
     * @throws Exception
     */
    @Test
    public void testAttuneInstanceInNonTestMode() throws Exception {
        boolean isTestMode   = false;
        Attune attune        = new Attune(isTestMode, config);
        RankingClient client = attune.getAttuneClient();

        assertTrue(client instanceof AttuneClient);
    }

}
