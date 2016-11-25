# Action: Increment

The Increment action increment the current value of a variable by 1.

This action should only be used on a var containing an Integer.

This action can be useful in case of custom loop or to keep track of a number of iteration.

>Please note that in the case of a foreach task a variable "i" is provided directly by MWDB to keep track of the iteration.

A simple way to call after having statically imported it is:

``` java
.then(increment("inc",1))
```

The first argument being the name of the variable to increment and the second one the increment step.
