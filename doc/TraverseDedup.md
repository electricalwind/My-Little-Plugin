# Action: Traverse Deduplicate

This action is similar to the traverse action *(note formerly get)* at the exception that the result will contain only one occurence of each node in case its a relation that is traverse

>Reminder:
The traverse action allows to get the value(s) of a node property. The property can be of any type, a relation, a map or a primitive Type.

This action should only be used after a task returning node(s) as result.
>If more than one node is present in the current result, then it's the aggregation of all the result that is stored keeping only one occurence.

A simple way to call after having statically imported it is:

``` java
.then(traverseDedup("name"))
```
The first argument being the attribute name to get.



Like the traverse *(formerly get)* action, the store get as var action accepts additional arguments to deal with Indexed relation in order to build a query.

``` java
 task()
                .then(readGlobalIndexAll("nodes"))
                .then(traverseDedup("children" "name","n0"))
                .then(readVar("children"))
```

As for other actions bulding Queries, arguments should follow the following pattern "key","value',"key","value",...


