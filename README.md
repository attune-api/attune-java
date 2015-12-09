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
  AttuneClient client       = AttuneClient.buildWith(config);
  ```

Visitors to the application should be tagged with an anonymous user id
```java
  AnonymousResult anonoymous = client.createAnonymous(authToken);
  System.out.println("Created anonymous user with id: " + anonymous.getId());
```

The user id can be bound to a customer id at login
```java
  AttuneClient client        = AttuneClient.buildWith(config);
  String authToken           = attuneClient.getAuthToken();
  AnonymousResult anonoymous = client.createAnonymous(authToken);
  Customer refCustomer       = new Customer();
  
  refCustomer.setCustomer("test-customer");
  client.bind(anonymous.getId(), refCustomer.getCustomer(), authToken);
```
The client can then perform rankings. Ranking calls compress the payload.

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

Ranking calls can set scope as follows:
```java
  RankingParams rankingParams = new RankingParams();
  rankingParams.setAnonymous(anon.getId());
  rankingParams.setView("/sales/57460");
  rankingParams.setEntitySource("scope");

  List<String> scope = new ArrayList<>();
  scope.add("sale=57460");
  scope.add("color=red");
  scope.add("size=M");
  rankingParams.setScope(scope); //Scope parameter that indicate what IDs to retrieve

  rankingParams.setEntityType("products");
  rankingParams.setApplication("event_page");

  List<String> idList = new ArrayList<String>();
  idList.add("1001");
  idList.add("1002");
  idList.add("1003");
  idList.add("1004");

  rankingParams.setIds(idList);

  RankedEntities rankings = client.getRankings(rankingParams, authToken);
```

The client provides a way to request a new auth_token through the API
```java
 AttuneClient client = AttuneClient.buildWith(attuneConfig);
 client.getAuthToken("consumer-name", "bearer-token");
```

### Configuration

attune.client.Attune is currently configured by {initial, runtime configuration properties} in the constructor of the AttuneConfigurable object. Here are the default values:
``` java
  // These are the initial configuration parameters i.e they can be set only once
  private String endpoint                       = "http://localhost";
  private double readTimeout                    = 0.25;
  private double connectionTimeout              = 0.50;
  private int maxPossiblePoolingConnections     = 1000;
  private int maxConnections                    = 200;
  
  // These parameters can be over ridden at runtime
  private boolean testMode                      = false;
  private boolean fallBackToDefault             = false;
```
There are multiple constructors to set the above parameters via the AttuneConfigurable object:

```java
  AttuneConfigurable();
  
  AttuneConfigurable(String endpoint);
  
  AttuneConfigurable(String endpoint, Integer maxPossiblePoolingConnections, Integer maxConnections, Double readTimeout, Double connectionTimeout);
  
  AttuneConfigurable(String endpoint, Integer maxPossiblePoolingConnections, Integer maxConnections);
  
  AttuneConfigurable(String endpoint, Double readTimeout, Double connectionTimeout);
```

To override the configuration values at runtime, we have provided API calls. Here is how the testMode and fallBackToDefault parameters can ve changed at runtime:
``` java
  AttuneClient client = AttuneClient.buildWith(attuneConfig);
  client.updateFallBackToDefault(true);
  client.updateTestMode(true); // no API calls to end point will be made, and rankings will be returned in their original order
```
The fallback to default mode when true, ensures that the rankings are default in case an exception occurs.

### Testing

For testing and development, the ranking API can be simulated using.

``` java
  
  boolean isTestMode            = true;
  attune.client.Attune attune   = new attune.client.Attune(isTestMode);
  RankingClient client          = attune.getAttuneClient();
  
```

In this test mode no API calls will be made, and rankings will be returned in their original order. This mode can be used during integration and should be turned off afterwards.


## Contributing

1. Fork it ( http://github.com/attune-api/attune-java/fork )
2. Create your feature branch (`git checkout -b my-new-feature`)
3. Commit your changes (`git commit -am 'Add some feature'`)
4. Push to the branch (`git push origin my-new-feature`)
5. Create new Pull Request
