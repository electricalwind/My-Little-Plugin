# Action: StoreGetAsVar

This action is similar to the get action at the exception that the current context result is not modify. The result of the get will be store in a variable.

>Reminder:
The get action allows to get the value(s) of a node property. The property can be of any type, a relation, a map or a primitive Type.

This action should only be used after a task returning node(s) as result.
>If more than one node is present in the current result, then it's the aggregation of all the get that is stored.

A simple way to call after having statically imported it is:

``` java
.then(storeGetAsVAr("name", "childrenName"))
```
The first argument being the attribute name to get and the second one the name of the var in which we should store the result of the get.

The main point of this action is that several get in a row can be call:
``` java
task()
    .then(readGlobalIndexAll("nodes"))
    .then(get("children"))
    .then(storeGetAsVAr("name", "childrenName"))
    .then(storeGetAsVAr("value", "childrenValue"))
```


Like the get action, the store get as var action accepts additional arguments to deal with Indexed relation in order to build a query.

``` java
 task()
                .then(readGlobalIndexAll("nodes"))
                .then(storeGetAsVAr("children", "children", "name","n0"))
                .then(readVar("children"))
```

As for other actions bulding Queries, arguments should follow the following pattern "key","value',"key","value",...


