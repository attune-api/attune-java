package attune.client;

import static attune.client.WireMockUtils.code404;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.util.concurrent.Uninterruptibles;

import attune.client.model.AnonymousResult;
import attune.client.model.Customer;
import attune.client.model.RankedEntities;
import attune.client.model.RankingParams;

/**
 * AttuneClient Tester.
 *
 * @author <sudnya>
 * @since <pre>May 27, 2015</pre>
 * @version 1.0
 */

public class AttuneClientTest {
    String authToken;
    AttuneConfigurable attuneConfig;
    @Before
    public void before() throws Exception {
//        this.authToken  = "cf5853d5-413a-4c5e-9d0b-1e7d7ad35911";
//        this.attuneConfig = new AttuneConfigurable("http://localhost:8765", 5000.0, 10000.0);
        //this.authToken  = "388dee30-394d-4a85-9e79-d951e5c3e292";
    	this.authToken = "a12a4e7a-b359-4c4f-aced-582673f2a6d9";
        this.attuneConfig = new AttuneConfigurable("https://api-test.attune.co", 2000d, 2000d);
    }

    @After
    public void after() throws Exception {
    }


    /**
     * Method: get auth token
     * @throws Exception
     */
    @Test
    public void testAuthGetToken() throws Exception {
    	pause("testAuthTokenGet");

        AttuneClient client = AttuneClient.buildWith(attuneConfig);

        assertNotNull(client.getAuthToken("attune", "a433de60fe2311e3a3ac0800200c9a66"));
        System.out.println("PASS: authToken not null");
    }

    /**
     * Method: test a anonymous get request
     * @throws Exception
     */
    @Test
    public void testCreateAnonymous() throws Exception {
    	pause("testAnonymousCreate");

        AttuneClient client = AttuneClient.buildWith(attuneConfig);
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
    public void testBind() throws Exception {
    	pause("testBind");

        AttuneClient client = AttuneClient.buildWith(attuneConfig);
        String authToken    = client.getAuthToken("attune", "a433de60fe2311e3a3ac0800200c9a66");
        assertNotNull(authToken);
        System.out.println("PASS: authToken not null");

        AnonymousResult anonymous = client.createAnonymous(authToken);
        assertNotNull(anonymous);
        System.out.println("PASS: anonymousResult not null");

        Customer customer = new Customer();
        customer.setCustomer("junit-test-customer" + new Date().getTime());
        client.bind(anonymous.getId(), customer.getCustomer(), authToken);
    }


    /**
     * Method: get customer id bound to an anonymous id
     * @throws Exception
     */
    @Test
    public void testGetBoundCustomer() throws Exception {
    	pause("testBoundCustomer");
        AttuneClient client = AttuneClient.buildWith(attuneConfig);
        String authToken = client.getAuthToken("attune", "a433de60fe2311e3a3ac0800200c9a66");
        assertNotNull(authToken);
        System.out.println("PASS: authToken not null");

        pause("testBoundCustomer");
        AnonymousResult anon = client.createAnonymous(authToken);
        assertNotNull(anon);
        System.out.println("PASS: anonymousResult not null");
        Customer cid = client.getBoundCustomer(anon.getId(), authToken);
        assertNotNull(cid);
    }

    /**
     * Method: verify that the bind happened correctly between anonymousId and customer
     * @throws Exception
     *
    @Test
    public void testCorrectConfigParams() throws Exception {
        AttuneClient client   = AttuneClient.getInstance(attuneConfig);
        String refEndPoint    = "https://api.attune-staging.co";

        System.out.println(client.getAttuneConfigurable().getEndpoint());

        String configEndPoint = client.getAttuneConfigurable().getEndpoint();
        assertTrue(configEndPoint.equals(refEndPoint));
    }

    /**
     * Method: verify that the bind happened correctly between anonymousId and customer
     * @throws Exception
     */
    @Test
    public void testBoundToCorrectCustomer() throws Exception {
        AttuneClient client = AttuneClient.buildWith(attuneConfig);

        AnonymousResult anon = client.createAnonymous(this.authToken);
        assertNotNull(anon);
        System.out.println("PASS: anonymousResult not null");

        Customer refCustomer = new Customer();
        refCustomer.setCustomer("junit-test-customer");
        client.bind(anon.getId(), refCustomer.getCustomer(), authToken);

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
        AttuneClient client = AttuneClient.buildWith(attuneConfig);
        AnonymousResult anon = client.createAnonymous(authToken);
        assertNotNull(anon);
        System.out.println("PASS: anonymousResult not null");

        RankingParams rankingParams = new RankingParams();
        rankingParams.setAnonymous("an-anon-id");
        rankingParams.setView("b/mens-pants");
        rankingParams.setEntityType("products");

        List<String> idList = withDefaultIdList();
        rankingParams.setIds(idList);

        RankedEntities rankings = client.getRankings(rankingParams, authToken);

        assertTrue(rankingParams.getEntitySource().toUpperCase().equals("IDS"));
        System.out.println("Default entity source is ids");

        assertNotNull(rankings);
        System.out.println("PASS: rankings not null");

        assertEquals(idList.size(), rankings.getRanking().size());
        System.out.println("PASS: size of results rankings equals size of product id list passed in ranking params i.e. " + idList.size());

        client.updateFallBackToDefault(true);
        RankedEntities defaultList = client.getRankings(rankingParams, authToken);

        assertTrue(idList.get(0).equals(defaultList.getRanking().get(0)));
        System.out.println("PASS: first entry of default (fallback mode on) results matches to those received in the request");
    }


    /**
     * Method: verify that the rankings returned on a get call happened correctly and the size of the list matches the list supplied in the params
     * Produces url - https://api-test.attune.co/entities/ranking?anonymous=some-anon-id&application=event_page&scope=sale%3D57460&scope=color%3Dred&scope=size%3DM&entityType=products
     * Yields a 404 from server
     * @throws Exception
     */
    @Test
    public void testScopeGetRankings404() throws ApiException {
    	List<String> idList = withDefaultIdList();
    	try {
	    	makeScopeRankingCall("1007", idList); // sale id doesnt exist
	        failBecauseExceptionWasNotThrown(ApiException.class);
    	} catch(ApiException e) {
    		assertThat(e).hasMessage("Client error occurred 404").hasNoCause().has(code404);
    	}
    }

    public void testScopeGetRankings() throws Exception {
        List<String> idList = withDefaultIdList();
        RankedEntities rankingParams = makeScopeRankingCall("59784", idList);
        assertThat(rankingParams).isNotNull();
    }

    public void testGzipGetRankings() throws Exception {
        AttuneClient client = AttuneClient.buildWith(attuneConfig);

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

        assertEquals(idList.size(), rankings.getRanking().size());
        System.out.println("PASS: size of results rankings equals size of product id list passed in ranking params i.e. " + idList.size());

        client.updateFallBackToDefault(true);
        RankedEntities defaultList = client.getRankings(rankingParams, authToken);

        assertTrue(idList.get(0).equals(defaultList.getRanking().get(0)));
        System.out.println("PASS: first entry of default (fallback mode on) results matches to those received in the request");
    }


	@Test
	public void testScopeGetRankingsOK() throws ApiException {
		List<String> idList = withDefaultIdList();
		RankedEntities rankings = makeScopeRankingCall("59784", idList);
	
	    assertNotNull(rankings);
	    System.out.println("PASS: rankings not null");
	}



    private List<String> withDefaultIdList() {
        List<String> idList = new ArrayList<String>();
        idList.add("1001");
        idList.add("1002");
        idList.add("1003");
        idList.add("1004");
        return idList;
    }

    //https://api-test.attune.co/entities/ranking?anonymous=some-anon-id&application=mobile_event_page&scope=sale%3D59784&scope=color%3Dred&scope=size%3DM&entityType=products\
    /**
     * 
     * @param saleId
     * @param idList Not used.  the scope ranking calls do not have an id list
     * @return
     * @throws ApiException
     */
    private RankedEntities makeScopeRankingCall(String saleId, List<String> idList) throws ApiException {
    	AttuneClient client = AttuneClient.buildWith(attuneConfig);

      RankingParams rankingParams = new RankingParams();
      rankingParams.setAnonymous("some-anon-id");
      rankingParams.setView("/sales/"+saleId);
      rankingParams.setEntitySource("scope");

      List<String> scope = new ArrayList<>();
      scope.add("sale="+saleId);
      scope.add("color=red");
      scope.add("size=M");
      rankingParams.setScope(scope); //Scope parameter that indicate what IDs to retrieve

      rankingParams.setEntityType("products");
      rankingParams.setApplication("mobile_event_page");

      client.updateFallBackToDefault(true);
      RankedEntities rankings = client.getRankings(rankingParams, authToken);
      return rankings;
    }

    private final static Boolean PAUSE = Boolean.FALSE;
    private void pause(String where) {
    	if (PAUSE) {
    		long sleepSeconds = 5;
			System.out.println(where + ": Sleep for " + sleepSeconds + "  seconds to not overwhelm api server with requests");
			Uninterruptibles.sleepUninterruptibly(5, TimeUnit.SECONDS);
    	}
    }
    /**
     * Method: verify that the rankings returned on a batchGetRankings call happened correctly and the size of the list matches the list supplied in the params
     * @throws Exception
     *
    @Test
    public void testBatchGetRankings() throws Exception {
        AttuneClient client = AttuneClient.buildWith(attuneConfig);

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

        RankingParams rankingParams2 = new RankingParams();
        rankingParams2.setAnonymous(anon.getId());
        rankingParams2.setView("sales/99876");
        rankingParams2.setEntityType("saleEvents");

        List<String> idList2 = new ArrayList<String>();
        idList2.add("9991");
        idList2.add("9992");
        idList2.add("9993");
        idList2.add("9994");

        rankingParams2.setIds(idList2);

        List<RankingParams> batchRankingParams = new ArrayList<>();
        batchRankingParams.add(rankingParams);
        batchRankingParams.add(rankingParams2);

        List<RankedEntities> batchRankings = client.batchGetRankings(batchRankingParams, authToken);

        assertNotNull(batchRankings);
        System.out.println("PASS: rankings not null");

        assertEquals(idList.size(), batchRankingParams.get(0).getIds().size());
        System.out.println("PASS: size of results rankings equals size of product id list passed in ranking params i.e. " + idList.size());

        assertEquals(idList2.size(), batchRankingParams.get(1).getIds().size());
        System.out.println("PASS: size of results rankings equals size of product id list passed in ranking params i.e. " + idList2.size());

        client.updateFallBackToDefault(true);
        List<RankedEntities> defaultBatchRankings = client.batchGetRankings(batchRankingParams, authToken);

        assertTrue(idList.get(0).equals(defaultBatchRankings.get(0).getRanking().get(0)));
        System.out.println("PASS: first entry of default (fallback mode on) results matches to those received in the request");
    }*/

}
