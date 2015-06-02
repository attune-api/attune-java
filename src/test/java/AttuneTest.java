import io.swagger.attune.AttuneClient;
import io.swagger.attune.MockClient;
import io.swagger.attune.RankingClient;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import static org.junit.Assert.*;

/**
 * Attune Tester.
 *
 * @author <sudnya>
 * @version 1.0
 * @since <pre>June 01, 2015</pre>
 */
public class AttuneTest {

    @Before
    public void before() throws Exception {
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
        Attune attune        = new Attune(isTestMode);
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
        Attune attune        = new Attune(isTestMode);
        RankingClient client = attune.getAttuneClient();

        assertTrue(client instanceof AttuneClient);
    }

}
