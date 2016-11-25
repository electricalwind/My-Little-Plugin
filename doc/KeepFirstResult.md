# Action: Keep First Result

The Keep First Result action modify the current context result to only keep its first element.

This action can be useful in case, several elements are present n the result and the following action doesn't handle this 
case.

A simple way to call after having statically imported it is:

``` java
.then(keepFirstResult())
```


> If less than 2 elements are present in the current context result this action has no effect.