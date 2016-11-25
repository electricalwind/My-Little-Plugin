# Action: InjectAsVar

This action is similar to the inject action at the exception that the current context result is not modify. The result of the get will be store in a variable.

>Reminder:
The inject action allows to put as current result any Object.



A simple way to call after having statically imported it is:

``` java
.then(injectAsVar("myvar", 1))
```
The first argument being the variable in which we should store the object and the second one the object itself.



