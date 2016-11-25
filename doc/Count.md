# Action: Count

The count action replace the current context result by an integer corresponding to its size.
Meaning that if 3 elements were present in the result, the new result will be 3.

This action is especially useful before control flow task like ifThenElse.

A simple way to call after having statically imported it is:

``` java
.then(count())
```


> If less than 2 elements are present in the current context result this action has no effect.