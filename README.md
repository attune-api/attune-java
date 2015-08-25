# attune.client.Attune

A client for the [attune.client.Attune ranking API](http://attune.co/).

* [Github](https://github.com/attune-api/attune-java)
* [Documentation] (https://github.com/attune-api/attune-java/blob/master/javadoc/index.html)

## Installation

Build a jar file using:
```groovy
gradle jar
```

The jar file is created at:
```
build/libs/attune-java.jar
```

Copy this jar to the build/libs folder of your project and import it.

## Usage

### Example usage

Requests are performed through a AttuneClient object
```java
  AttuneConfigurable config = new AttuneConfigurable(endpoint, timeout, clientId, clientSecret);
  AttuneClient client       = AttuneClient.getInstance(config);
  ```

Visitors to the application should be tagged with an anonymous user id
```java
  AnonymousResult anonoymous = client.createAnonymous(authToken);
  System.out.println("Created anonymous user with id: " + anonymous.getId());
```

The user id can be bound to a customer id at login
```java
  AttuneClient client        = AttuneClient.getInstance(config);
  String authToken           = attuneClient.getAuthToken();
  AnonymousResult anonoymous = client.createAnonymous(authToken);
  Customer refCustomer       = new Customer();
  
  refCustomer.setCustomer("test-customer");
  client.bind(anonymous.getId(), refCustomer.getCustomer(), authToken);
```
The client can then perform rankings. Ranking calls can be either single ranking calls or batched ranking calls.

Here is an example of a single ranking call:
```java
  RankingParams rankingParams = new RankingParams();
  
  rankingParams.setAnonymous(anonymous.getId());
  rankingParams.setView("b/mens-pants");
  rankingParams.setEntityType("products");

  List<String> idList = new ArrayList<String>();
  idList.add("1001");
  idList.add("1002");
  idList.add("1003");
  idList.add("1004");

  rankingParams.setIds(idList);

  RankedEntities rankings = client.getRankings(rankingParams, authToken);
```

Batched ranking calls can be used as follows:
```java
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

```

The client provides a way to request a new auth_token through the API
```java
  AttuneClient client = AttuneClient.getInstance();
  String authToken    = attuneClient.getAuthToken();
```

### Configuration

attune.client.Attune is currently configured by setting properties in the config.properties file of the project. Here are the default values:
``` java

end_point=https://api.attune-staging.co
timeout=5
client_id=client-id
client_secret=client-secret
test_mode=true
```

To override these values, we have provided API calls. Here is how the properties like end_point, timeout, client_id, client_secret, test_mode can be overridden:
``` java
  AttuneClient client = AttuneClient.getInstance();
  client.setEndpoint("http://localhost");
  client.setTimeout(0.50); //time the client waits on a read connection before timing-out 0.50 = 500 millisec
  client.setClientId("test-client-id");
  client.setClientSecret("test-client-secret");
  client.setDefaultRankingOnError("fallback_to_default") // When there is a server exception, a value of 'true' displays default ranking instead of showing the exception
  client.setTestMode(true); // no API calls to end point will be made, and rankings will be returned in their original order
```

Settings can also be overridden by modifying the config.properties file

### Testing

For testing and development, the ranking API can be simulated using.

``` java
  
  boolean isTestMode   = true;
  attune.client.Attune attune        = new attune.client.Attune(isTestMode);
  RankingClient client = attune.getAttuneClient();
  
```

In this mode no API calls will be made, and rankings will be returned in their original order.


## Contributing

1. Fork it ( http://github.com/attune-api/attune-java/fork )
2. Create your feature branch (`git checkout -b my-new-feature`)
3. Commit your changes (`git commit -am 'Add some feature'`)
4. Push to the branch (`git push origin my-new-feature`)
5. Create new Pull Request
