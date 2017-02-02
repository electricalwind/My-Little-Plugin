# Action: Read Updated Time

This action is similar to the readVar action at the exception that if the var contains nodes, the nodes travel to the current context time. 


A simple way to call after having statically imported it is:

``` java
.then(readUpdatedTimeVar("myvar"))
```
The first argument being the variable to read from.



