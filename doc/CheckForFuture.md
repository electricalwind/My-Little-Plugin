# Action: Check For Future

The check for future action checks if nodes present in the current context result have modification in the future. If yes an exception is returned. This action is meant to prevent any modification in the past and should hence be call before every call that might lead to such a situation.


A simple way to call after having statically imported it is:

``` java
.then(checkForFuture())
```

 > Limitation: the action is currentluy not handling worlds.