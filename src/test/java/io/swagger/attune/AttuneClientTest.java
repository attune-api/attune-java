package io.swagger.attune;

import io.swagger.attune.AttuneClient;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import static org.junit.Assert.*;

/**
 * AttuneClient Tester.
 *
 * @author <sudnya>
 * @since <pre>May 27, 2015</pre>
 * @version 1.0
 */

public class AttuneClientTest {

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: get auth token
     */
    @Test
    public void testAuthTokenGet() throws Exception {
        AttuneClient client = new AttuneClient();
        assertNotNull(client.getAuthToken("attune", "a433de60fe2311e3a3ac0800200c9a66"));
    }

    @Test
    public void testAnonymousCreate() throws Exception {
        AttuneClient client = new AttuneClient();
        String access_token = client.getAuthToken("attune", "a433de60fe2311e3a3ac0800200c9a66");
        assertNotNull(client.createAnonymous(access_token));
    }

}


