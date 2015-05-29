package io.swagger.attune;

import io.swagger.attune.AttuneClient;
import io.swagger.attune.model.AnonymousResult;
import io.swagger.attune.model.Customer;
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
     * @throws Exception
     */
    @Test
    public void testAuthTokenGet() throws Exception {
        System.out.println("testAuthTokenGet: Sleep for 60 seconds to not overwhelm api server with requests");
        Thread.sleep(60*1000L);
        AttuneClient client = new AttuneClient();
        assertNotNull(client.getAuthToken("attune", "a433de60fe2311e3a3ac0800200c9a66"));
    }

    /**
     * Method: test a anonymous get request
     * @throws Exception
     */
    @Test
    public void testAnonymousCreate() throws Exception {
        System.out.println("testAnonymousCreate: Sleep for 60 seconds to not overwhelm api server with requests");
        Thread.sleep(60*1000L);
        AttuneClient client = new AttuneClient();
        String authToken = client.getAuthToken("attune", "a433de60fe2311e3a3ac0800200c9a66");
        assertNotNull(client.createAnonymous(authToken));
    }

    /**
     * Method: get customer id bound to an anonymous id
     * @throws Exception
     */
    @Test
    public void testBind() throws Exception {
        AttuneClient client    = new AttuneClient();
        String authToken       = client.getAuthToken("attune", "a433de60fe2311e3a3ac0800200c9a66");
        System.out.println("testBind: Sleep for 60 seconds to not overwhelm api server with requests");
        Thread.sleep(60 * 1000L);
        AnonymousResult anonId = client.createAnonymous(authToken);
        System.out.println("testBind: Sleep for 60 seconds to not overwhelm api server with requests");
        Thread.sleep(60*1000L);
        Customer cid = client.getBinding(anonId.getId(), authToken);
        System.out.println("Bind returned " + cid.getCustomer());
        assertNotNull(cid);
    }
}


