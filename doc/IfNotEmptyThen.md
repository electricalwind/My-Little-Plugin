# Action: If Not Empty Then

This action and its sibling if empty are similar to the IfThen task at the exception that no condition are required.

This action will launch a task given as an argument if the current result is not empty.

A simple way to call after having statically imported it is:

``` java
.then(ifNotEmptyThen(
                        newTask()
                                .addToVar("myvar")
                        )
                )
```