package io.swagger.attune;

import io.swagger.attune.model.AnonymousResult;
import io.swagger.attune.model.Customer;
import io.swagger.attune.model.RankedEntities;
import io.swagger.attune.model.RankingParams;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        System.out.println("PASS: authToken not null");
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
        String authToken    = client.getAuthToken("attune", "a433de60fe2311e3a3ac0800200c9a66");
        assertNotNull(authToken);
        System.out.println("PASS: authToken not null");

        assertNotNull(client.createAnonymous(authToken));
        System.out.println("PASS: anonymousResult not null");
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

        AttuneClient client       = new AttuneClient();
        String authToken          = client.getAuthToken("attune", "a433de60fe2311e3a3ac0800200c9a66");
        assertNotNull(authToken);
        System.out.println("PASS: authToken not null");

        AnonymousResult anonymous = client.createAnonymous(authToken);
        assertNotNull(anonymous);
        System.out.println("PASS: anonymousResult not null");

        Customer customer         = new Customer();
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
        assertNotNull(authToken);
        System.out.println("PASS: authToken not null");

        System.out.println("testBoundCustomer: Sleep for " + sleepSeconds + "  seconds to not overwhelm api server with requests");
        Thread.sleep(sleepSeconds * 1000L);

        AnonymousResult anon = client.createAnonymous(authToken);
        assertNotNull(anon);
        System.out.println("PASS: anonymousResult not null");

        System.out.println("testBoundCustomer: Sleep for " + sleepSeconds + "  seconds to not overwhelm api server with requests");
        Thread.sleep(sleepSeconds * 1000L);

        Customer cid = client.getBoundCustomer(anon.getId(), authToken);
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
        System.out.println("Customer Id correctly set to: " + resultCustomer.getCustomer() + " via bind call Customer Id: " + refCustomer.getCustomer());
    }


    /**
     * Method: verify that the rankings returned on a get call happened correctly and the size of the list matches the list supplied in the params
     * @throws Exception
     */
    @Test
    public void testGetRankings() throws Exception {
        long sleepSeconds      = 30;
        AttuneClient client    = new AttuneClient();

        System.out.println("testGetRankings: Sleep for " + sleepSeconds + "  seconds to not overwhelm api server with requests");
        Thread.sleep(sleepSeconds * 1000L);

        String authToken       = client.getAuthToken("attune", "a433de60fe2311e3a3ac0800200c9a66");
        assertNotNull(authToken);
        System.out.println("PASS: authToken not null");

        System.out.println("testGetRankings: Sleep for " + sleepSeconds + "  seconds to not overwhelm api server with requests");
        Thread.sleep(sleepSeconds * 1000L);

        AnonymousResult anon = client.createAnonymous(authToken);
        assertNotNull(anon);
        System.out.println("PASS: anonymousResult not null");

        RankingParams rankingParams = new RankingParams();
        rankingParams.setAnonymous(anon.getId());
        rankingParams.setView("b/mens-pants");
        rankingParams.setEntityType("products");

        List<String> idList = new ArrayList<String>();
        idList.add("1001");
        idList.add("1002");
        idList.add("1003");
        idList.add("1004");

        rankingParams.setIds(idList);

        RankedEntities rankings = client.getRankings(rankingParams, authToken);

        assertNotNull(rankings);
        System.out.println("PASS: rankings not null");

        assertEquals(idList.size(), rankings.getRanking().size());
        System.out.println("PASS: size of results rankings equals size of product id list passed in ranking params i.e. " + idList.size());
    }
}


