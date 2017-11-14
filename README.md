# lt

This is akka http service with akka actors handling the request
Some problems I run into:
- New Akka http framework, need to lear it, even it's similar to spray
- Create twitter app 
- Understand twitter api
- Understand twitter4j api library

I spend about 12 hours. 
- Bootstrapping take about 2 hours
- Understanding twitter api, twitter apps and twitter4j api library about another 2 hores
- Service take about  4 hours
- Testing take 4 hours



-To build it execute
```
./gradlew clean test build distZip
```

- This will create a zip under build/distributions/letShout-1.0.0.zip

- Unzip it and enter into the directory 
```letShout-1.0.0```

- Make sure to set twitter env variables
```
export TWITTER_OAUTH_CONSUMER_KEY=XXXX
export TWITTER_OAUTH_CONSUMER_SECRET=XXXX
export TWITTER_OAUTH_ACCESS_TOKEN=XXXX
export TWITTER_OAUTH_ACCESS_TOKEN_SECRET=XXXX
```

- Execute it with 
```./bin/letShout```



