# Attune

A client for the [Attune ranking API](http://attune.co/).

* [Github](https://github.com/attune-api/attune-java)
* [Documentation] (https://github.com/attune-api/attune-java/blob/master/javadoc/index.html)

## Installation
Build a jar file using:
gradle jar

The jar file is created at:
build/libs/attune-java.jar

Copy this jar to the build/libs folder of your project and import it.

## Usage

### Example usage

Requests are performed through a AttuneClient object
```java
  AttuneClient attuneClient = new AttuneClient();
```

Visitors to the application should be tagged with an anonymous user id
```java
  AnonymousResult anonoymous = client.createAnonymous(authToken);
  System.out.println("Created anonymous user with id: " + anonymous.getId());
```

The user id can be bound to a customer id at login
```java
  AttuneClient client        = new AttuneClient();
  String authToken           = attuneClient.getAuthToken(clientId, clientSecret);
  AnonymousResult anonoymous = client.createAnonymous(authToken);
  Customer refCustomer       = new Customer();
  
  refCustomer.setCustomer("test-customer");
  client.bind(anonymous.getId(), refCustomer.getCustomer(), authToken);
```
The client can then perform rankings

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

The client provides a way to request a new auth_token through the API
```java
  AttuneClient attuneClient = new AttuneClient();
  String authToken          = attuneClient.getAuthToken(clientId, clientSecret);
```

## Contributing

1. Fork it ( http://github.com/attune-api/attune-java/fork )
2. Create your feature branch (`git checkout -b my-new-feature`)
3. Commit your changes (`git commit -am 'Add some feature'`)
4. Push to the branch (`git push origin my-new-feature`)
5. Create new Pull Request
