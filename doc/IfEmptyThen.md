# Action: If Empty Then

This action and its sibling if not empty are similar to the IfThen task at the exception that no condition are required.

This action will launch a task given as an argument if the current result is empty. 

This action is especially useful after connecting the graph to launch an initialization task if its the graph was just created.

A simple way to call after having statically imported it is:

``` java
.then(ifEmptyThen(
                        newTask()
                                .inject("content")
                                .addToVar("myvar")
                        )
                )
```


