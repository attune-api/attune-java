package attune.client;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.netflix.config.ConfigurationManager;
import com.netflix.config.DynamicConfiguration;
import com.netflix.hystrix.exception.HystrixRuntimeException;

import attune.client.api.Anonymous;
import attune.client.api.Entities;
import attune.client.hystrix.BaseApiHystrixCommand;
import attune.client.hystrix.BindHystrixCommand;
import attune.client.hystrix.CreateAnonymousHystrixCommand;
import attune.client.hystrix.GetBoundCustomerHystrixCommand;
import attune.client.hystrix.GetRankingsGETHystrixCommand;
import attune.client.hystrix.GetRankingsHystrixCommand;
import attune.client.model.AnonymousResult;
import attune.client.model.Customer;
import attune.client.model.RankedEntities;
import attune.client.model.RankingParams;

/**
 * Created by sudnya on 5/27/15.
 */
public class AttuneClient implements RankingClient  {

    private AttuneConfigurable attuneConfigurable;
    private Entities entities;
    private Anonymous anonymous;


    private AttuneClient(AttuneConfigurable configurable) {
        attuneConfigurable = configurable;
        entities           = new Entities(attuneConfigurable);
        anonymous          = new Anonymous(attuneConfigurable);

        initializeHystrixConfig(configurable);
    }

    private static DynamicConfiguration dynamicConfig;
    private void initializeHystrixConfig(AttuneConfigurable configurable) {
    	if (!ConfigurationManager.isConfigurationInstalled()) {
	    	HystrixConfig.Builder hystrixConfigBuilder = new HystrixConfig.Builder();
	
	        hystrixConfigBuilder = (configurable.isFallBackToDefault()) ? hystrixConfigBuilder.enableFallback() : hystrixConfigBuilder.disableFallback();
	
	    	HystrixConfig hystrixConfig = hystrixConfigBuilder.withTimeoutInMilliseconds(new Double(configurable.getReadTimeout() * 1000).intValue()).build();
	
	        dynamicConfig     = new DynamicConfiguration();
	        System.out.println(dynamicConfig);
	        Set<Map.Entry<String, Object>> entries = hystrixConfig.getParams().entrySet();
	
	    	for (Map.Entry<String, Object> entry : entries) {
	    		dynamicConfig.addProperty(entry.getKey(), entry.getValue());
	    	}
    		ConfigurationManager.install(dynamicConfig);            
    	}
    }


    /**
     * Returns an instance of the client configured based on the provided {@link AttuneConfigurable} instance.
     * @param config configuration
     * @return instance of the client
     */
    public static AttuneClient buildWith(AttuneConfigurable config) {
        return new AttuneClient(config);
    }

    /**
     * Overrides the default value of the fallBackToDefault mode
     * @author sudnya
     * @param defaultFallBack
     */
    public void updateFallBackToDefault(boolean defaultFallBack) {
    	for (String cmdKey:HystrixConfig.HYSTRIX_COMMANDS) {
    		String cmdPropertyNameStub = HystrixConfig.propertyNameStub + cmdKey;
    		dynamicConfig.setProperty(cmdPropertyNameStub + ".fallback.enabled", defaultFallBack);
    	}
        attuneConfigurable.updateFallbackToDefaultMode(defaultFallBack);
    }

    /**
     * Overrides the default value of the fallBackToDefault mode
     * @author sudnya
     * @param defaultFallBack
     */
    public void updateTimeout(int timeoutMillis) {
    	for (String cmdKey:HystrixConfig.HYSTRIX_COMMANDS) {
    		String cmdPropertyNameStub = HystrixConfig.propertyNameStub + cmdKey;
    		dynamicConfig.setProperty(cmdPropertyNameStub + ".execution.isolation.thread.timeoutInMilliseconds", timeoutMillis);
    	}
        //attuneConfigurable.updateFallbackToDefaultMode(timeoutMillis);
    }

    /**
     * Overrides the default value of the test mode
     * @author sudnya
     * @param testMode
     */
    public void updateTestMode(boolean testMode) {
        attuneConfigurable.updateTestMode(testMode);
    }

    public String getAuthToken(String clientId, String clientSecret) throws ApiException {
        int counter = 0;

        if (clientId == null)
            throw new IllegalArgumentException("clientId is required");
        if (clientSecret == null)
            throw new IllegalArgumentException("clientSecret is required");

        Client client                             = ClientBuilder.newClient();
        WebTarget authResource                    = client.target(attuneConfigurable.getEndpoint()).path("oauth/token");
        MultivaluedMap<String, String> formData   = new MultivaluedHashMap<String, String>();

        formData.add("client_id"    , clientId);
        formData.add("client_secret", clientSecret);
        formData.add("grant_type"   , "client_credentials");

        String accessToken = null;
        Response response  = authResource.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.form(formData));
        String body        = response.readEntity(String.class);

        while (true) {
            try {
                JSONObject json = new JSONObject(body);
                accessToken = json.getString("access_token");
                break;
            } catch (JSONException e) {
                ++counter;
                if (counter > getNumTries()) {
                    throw new ApiException();
                }
            }
        }
        return accessToken;
    }


    /**
     * Requests an anonymous id, given an auth token
     * @author sudnya
     * @param authToken authentication token
     * @return An AnonymousResult object, do a getId on this object to get anonymousId
     */
    public AnonymousResult createAnonymous(String authToken) throws ApiException {
        AnonymousResult retVal = executeCommand(new CreateAnonymousHystrixCommand(anonymous, authToken, getNumTries()));
        return retVal;
    }

    /**
     * Binds one actor to another, allowing activities of those actors to be shared between the two.
     * @author sudnya
     * @param anonymousId anonymousId
     * @param customerId customerId
     * @param authToken authentication token
     */
    public void bind(String anonymousId, String customerId, String authToken) throws ApiException {
        Customer customer = new Customer();
        customer.setCustomer(customerId);
        executeCommand(new BindHystrixCommand(anonymous, anonymousId, customer, authToken, getNumTries()));
    }


    /**
     * Returns the customer bound to a given anonymousId, for a given auth token
     * @author sudnya
     * @param anonymousId anonymousId
     * @param authToken authentication token
     * @return A customer that was associated to this anonymousId with a bind call
     */
    public Customer getBoundCustomer(String anonymousId, String authToken) throws ApiException {
        Customer retVal = executeCommand(new GetBoundCustomerHystrixCommand(anonymous, anonymousId, authToken, getNumTries()));
        return retVal;
    }

    /**
     * Returns a ranking of the specified entities, given an auth token
     * @author sudnya
     * @param rankingParams an object with the ranking parameters
     * @param authToken authentication token
     */
    public RankedEntities getRankings(RankingParams rankingParams, String authToken) throws ApiException {
    	GetRankingsGETHystrixCommand getRankingsHystrixCommand;
    	if (rankingParams.getEntitySource().toUpperCase().equals("IDS")) {
    		getRankingsHystrixCommand = new GetRankingsHystrixCommand(entities, rankingParams, authToken, getNumTries());
    	} else {
    		getRankingsHystrixCommand = new GetRankingsGETHystrixCommand(entities, rankingParams, authToken, getNumTries());
    	}
    	return executeCommand(getRankingsHystrixCommand);
    }

    private <T> T executeCommand(BaseApiHystrixCommand<T> cmd) throws ApiException {
    	try {
    		T retVal = cmd.execute();
    		return retVal;
    	} catch(HystrixRuntimeException e) {
    		throw unwrap(e);
    	}
    }

    private ApiException unwrap(HystrixRuntimeException e) {
    	ApiException ex;
    	if (e.getCause() instanceof ApiException) {
    		ex = (ApiException) e.getCause();
    	} else {
    		if (e.getCause() instanceof TimeoutException) {
    			ex = new ApiException(Status.GATEWAY_TIMEOUT.getStatusCode(), e.getCause());
    		} else {
    			ex = new ApiException(Status.INTERNAL_SERVER_ERROR.getStatusCode(), e.getCause());
    		}
    	}
    	return ex;
    }

    /**
     * Returns a list of rankings of the given list of specified entities, given an auth token
     * @author sudnya
     * @param rankingParamsList list of objects with the ranking parameters
     * @param authToken authentication token
     *
    public List<RankedEntities> batchGetRankings(List<RankingParams> rankingParamsList, String authToken) throws ApiException {
        int counter = 0;
        List<RankedEntities> retVal;
        BatchRankingRequest batchRequest = new BatchRankingRequest();
        batchRequest.setRequests(rankingParamsList);
        BatchRankingResult result;

        while (true) {
            try {
                result = entities.batchGetRankings(batchRequest, authToken);
                break;
            } catch (ApiException ex) {
                ++counter;
                if (counter > MAX_RETRIES) {
                    if (attuneConfigurable.isFallBackToDefault()) {
                        return returnBatchDefaultRankings(rankingParamsList);
                    }
                    throw ex;
                }
            }
        }
        retVal = result.getResults();
        return retVal;
    }*/


/**
    private List<RankedEntities> returnBatchDefaultRankings(List<RankingParams> rankingParamsList) {
        List<RankedEntities> rankedEntityList = new ArrayList<>();
        for (RankingParams entry : rankingParamsList) {
            rankedEntityList.add(returnDefaultRankings(entry));
        }
        return rankedEntityList;
    }
*/

  //TODO: this is for junit test purpose only, hence don't generate javadoc
    protected AttuneConfigurable getAttuneConfigurable() {
        return this.attuneConfigurable;
    }

    private int getNumTries() {
    	return 1 + this.attuneConfigurable.getRetryCount();
    }
}
