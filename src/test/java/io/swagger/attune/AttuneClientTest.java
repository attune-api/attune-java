package io.swagger.attune;

import io.swagger.attune.model.AnonymousResult;
import io.swagger.attune.model.Customer;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.Date;

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
        long sleepSeconds = 30;
        System.out.println("testAuthTokenGet: Sleep for " + sleepSeconds + " seconds to not overwhelm api server with requests");
        Thread.sleep(sleepSeconds*1000L);
        AttuneClient client = new AttuneClient();
        assertNotNull(client.getAuthToken("attune", "a433de60fe2311e3a3ac0800200c9a66"));
    }

    /**
     * Method: test a anonymous get request
     * @throws Exception
     */
    @Test
    public void testAnonymousCreate() throws Exception {
        long sleepSeconds = 30;
        System.out.println("testAnonymousCreate: Sleep for  " + sleepSeconds + " seconds to not overwhelm api server with requests");
        Thread.sleep(sleepSeconds * 1000L);
        AttuneClient client = new AttuneClient();
        String authToken = client.getAuthToken("attune", "a433de60fe2311e3a3ac0800200c9a66");
        assertNotNull(client.createAnonymous(authToken));
    }

    /**
     * Method: test a binding call request
     * @throws Exception
     */
    @Test
    public void testBindCall() throws Exception {
        long sleepSeconds = 30;
        System.out.println("testBind: Sleep for  " + sleepSeconds + "  seconds to not overwhelm api server with requests");
        Thread.sleep(sleepSeconds * 1000L);
        AttuneClient client = new AttuneClient();
        String authToken = client.getAuthToken("attune", "a433de60fe2311e3a3ac0800200c9a66");
        AnonymousResult anonymous = client.createAnonymous(authToken);
        Customer customer = new Customer();
        customer.setCustomer("junit-test-customer" + new Date().getTime());
        client.bind(anonymous.getId(), customer.getCustomer(), authToken);
    }
    /**
     * Method: get customer id bound to an anonymous id
     * @throws Exception
     */
    @Test
    public void testBoundCustomer() throws Exception {
        long sleepSeconds      = 30;
        AttuneClient client    = new AttuneClient();
        System.out.println("testBoundCustomer: Sleep for " + sleepSeconds + "  seconds to not overwhelm api server with requests");
        Thread.sleep(sleepSeconds * 1000L);
        String authToken       = client.getAuthToken("attune", "a433de60fe2311e3a3ac0800200c9a66");
        System.out.println("testBoundCustomer: Sleep for " + sleepSeconds + "  seconds to not overwhelm api server with requests");
        Thread.sleep(sleepSeconds * 1000L);
        AnonymousResult anonId = client.createAnonymous(authToken);
        System.out.println("testBoundCustomer: Sleep for " + sleepSeconds + "  seconds to not overwhelm api server with requests");
        Thread.sleep(sleepSeconds * 1000L);
        Customer cid = client.getBoundCustomer(anonId.getId(), authToken);
        assertNotNull(cid);
    }

    /**
     * Method: verify that the bind happened correctly between anonymousId and customer
     * @throws Exception
     */
    @Test
    public void testBoundToCorrectCustomer() throws Exception {
        long sleepSeconds      = 30;
        AttuneClient client    = new AttuneClient();
        System.out.println("testBoundToCorrectCustomer: Sleep for " + sleepSeconds + "  seconds to not overwhelm api server with requests");
        Thread.sleep(sleepSeconds * 1000L);
        String authToken       = client.getAuthToken("attune", "a433de60fe2311e3a3ac0800200c9a66");
        assertNotNull(authToken);
        System.out.println("PASS: authToken not null");
        System.out.println("testBoundToCorrectCustomer: Sleep for " + sleepSeconds + "  seconds to not overwhelm api server with requests");
        Thread.sleep(sleepSeconds * 1000L);
        AnonymousResult anon = client.createAnonymous(authToken);
        assertNotNull(anon);
        System.out.println("PASS: anonymousResult not null");
        System.out.println("testBoundToCorrectCustomer: Sleep for " + sleepSeconds + "  seconds to not overwhelm api server with requests");
        Thread.sleep(sleepSeconds*1000L);
        Customer refCustomer = new Customer();
        refCustomer.setCustomer("junit-test-customer");
        client.bind(anon.getId(), refCustomer.getCustomer(), authToken);
        System.out.println("testBoundToCorrectCustomer: Sleep for " + sleepSeconds + "  seconds to not overwhelm api server with requests");
        Thread.sleep(sleepSeconds*1000L);
        Customer resultCustomer = client.getBoundCustomer(anon.getId(), authToken);
        assertNotNull(resultCustomer);
        System.out.println("PASS: bound customer not null");
        assertEquals(refCustomer.getCustomer(), resultCustomer.getCustomer());
    }
}


