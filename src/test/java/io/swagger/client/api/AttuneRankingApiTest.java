package io.swagger.client.api;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import static org.junit.Assert.*;

/**
 * AttuneRankingApi Tester.
 *
 * @author <sudnya>
 * @since <pre>May 25, 2015</pre>
 * @version 1.0
 */
public class AttuneRankingApiTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: create
     */
    @Test
    public void testAnonymousCreate() throws Exception {
        AttuneRankingApi attuneRankingApi = new AttuneRankingApi();
        assertNotNull(attuneRankingApi.create());
    }

}