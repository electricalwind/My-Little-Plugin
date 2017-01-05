# Action: If Empty Then Else

This action and its sibling if not empty are similar to the IfThenElse task at the exception that no condition are required.

This action will launch a task given as an argument if the current result is empty and another if not. 

This action is especially useful in a creating or update situation

A simple way to call after having statically imported it is:

``` java
.then(ifEmptyThenElse(
                        newTask()
                                .inject("content")
                                .addToVar("myvar")
                        ,
                        newTask()
                                .inject("anothercontent")
                                .addToVar("myOtherVar")
                        )
                )
```
