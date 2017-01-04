# Action: TraverseOrAttributeInVar

This action is similar to the traverse action *(note formerly get)* at the exception that the current context result is not modify. The result of the get will be store in a variable.

>Reminder:
The get action allows to get the value(s) of a node property. The property can be of any type, a relation, a map or a primitive Type.

This action should only be used after a task returning node(s) as result.
>If more than one node is present in the current result, then it's the aggregation of all the get that is stored.

A simple way to call after having statically imported it is:

``` java
.then(traverseOrAttributeInVar("name", "childrenName"))
```
The first argument being the attribute name to get and the second one the name of the var in which we should store the result of the get.

The main point of this action is that several traverse *(formerly get)* in a row can be call:
``` java
task()
    .then(readGlobalIndex("nodes"))
    .then(traverse("children"))
    .then(traverseOrAttributeInVar("name", "childrenName"))
    .then(traverseOrAttributeInVar("value", "childrenValue"))
```


Like the traverse *(formerly get)* action, the store get as var action accepts additional arguments to deal with Indexed relation in order to build a query.

``` java
 task()
                .then(readGlobalIndexAll("nodes"))
                .then(traverseOrAttributeInVar("children", "children", "name","n0"))
                .then(readVar("children"))
```

As for other actions bulding Queries, arguments should follow the following pattern "key","value',"key","value",...


