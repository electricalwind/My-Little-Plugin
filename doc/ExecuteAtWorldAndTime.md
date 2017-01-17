# Action: Execute At Word And Time

This action allows one to execute a given task at a given world and time and then come back to the current time.

This action is especially useful for node initialization, when some nodes have to be created at the beginning of time for example.

The parameters of this action are the following

* world String containing the long value or the name of the variable in which the long is stored
* time String containing the long value or the name of the variable in which the long is stored
* task to execute

A simple way to call after having statically imported it is:

``` java
.then(executeAtWorldAndTime("0", "1",
                        newTask()
                                .createNode()
                                .setAttribute("name", Type.STRING, "newNode")
                                .defineAsVar("newNode")
                                .readGlobalIndex("roots")
                                .addVarToRelation("children", "newNode")
                        )
                )
```