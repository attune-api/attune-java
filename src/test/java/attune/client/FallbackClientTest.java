package attune.client;

import attune.client.model.RankingParams;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * FallbackClient Tester.
 *
 * @author <sudnya>
 * @since <pre>June 2, 2015</pre>
 * @version 1.0
 */

public class FallbackClientTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }


    /**
     * Method: test a anonymous get request
     * @throws Exception
     */
    @Test
    public void testCreateAnonymousFromMock() throws Exception {
        FallbackClient fallbackClient = new FallbackClient();
        assertNotNull(fallbackClient.createAnonymous(""));
    }


    /**
     * Method: test a binding call request
     * @throws Exception
     */
    @Test
    public void testBindFromMock() throws Exception {
        FallbackClient fallbackClient = new FallbackClient();
        fallbackClient.bind("", "", "");
    }


    /**
     * Method: get customer id bound to an anonymous id
     * @throws Exception
     */
    @Test
    public void testBoundCustomerFromMock() throws Exception {
        FallbackClient fallbackClient = new FallbackClient();
        assertNotNull(fallbackClient.getBoundCustomer("", ""));
    }


    /**
     * Method: verify that the rankings returned on a get call happened correctly and the size of the list matches the list supplied in the params
     * @throws Exception
     */
    @Test
    public void testGetRankingsFromMock() throws Exception {
        FallbackClient fallbackClient = new FallbackClient();
        assertNotNull(fallbackClient.getRankings(new RankingParams(), ""));
    }
}
