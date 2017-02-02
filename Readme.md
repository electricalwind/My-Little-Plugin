# My Little Plugin

## For Many World Graph Database 

<img src="doc/img/logo.jpg" width="200px" /><img src="doc/img/4.png" width="150px"/><img src="doc/img/logo.png" width="250px" />

![MWDB-version](https://img.shields.io/badge/MWDB--version-11--SNAPSHOT-green.svg)

![version](https://img.shields.io/badge/version-1.0-blue.svg)

This library bring additional actions to the [MWDB](https://github.com/kevoree-modeling/mwDB) project. This library is compatible with the latest MWDB api, that will be introduced in the 11th version. 
Compatibility with previous versions of MWDB api is not supported anymore.

### Actions?

Actions are reusable elements hiding low-level, asynchronous task primitives behind an expressive API.
 Actions allows to traverse and manipulate the graph and can be chained to form a task. 

### Why additional Action?

A great number of action are already implemented and available in mwdb, please refer to the  org.mwg.core.task package of MWDB. 
However, thinking and implementing every possible actions would: 
1) be impossible 
2) drastically increase the size of the project. 

That's why only basic, i.e., atomic, actions are implemented. 

Yet, users might need more composed actions like Injecting an object as a variable or Putting the result of a get action in a variable instead of in the result. 
This library aims at providing such actions and will evolves alongside MWDB, i.e.,  some actions might be added as a need for it is express and other removed to be directly integrated within MWDB.

### What are those Actions

Currently offered actions are: 

* [Check For Future](doc/CheckForFuture.md)
* [Count](doc/Count.md)
* [Execute at World and Time](doc/ExecuteAtWorldAndTime.md)
* [Traverse Or Attribute in Var](doc/TraverseOrAttributeInVar.md)
* [If empty then](doc/IfEmptyThen.md)
* [If empty then else](doc/IfEmptyThenElse.md)
* [If not empty then](doc/IfNotEmptyThen.md)
* [If not empty then else](doc/IfNotEmptyThenElse.md)
* [Inject as Var](doc/InjectAsVar.md)
* [Increment](doc/Increment.md)
* [Keep first result](doc/KeepFirstResult.md)
* [Flip Vars](doc/FlipVars.md)
* [Flip Var And Result](doc/FlipVarAndResult.md)
* [Read Updated Time Var](doc/ReadUpdatedTimeVar.md)
 
### Can I create my own action?

Of course, feel free to take a look at the project to see how to implement your own action and see how to integrate them to your project.

### How to use this library?

The library provides a maven configuration file, so just download the project, intall it using mvn install and add the following dependency to your project.
 
         <dependency>
             <groupId>lu.jimenez.research</groupId>
             <artifactId>my-little-plugin</artifactId>
             <version>$version</version>
         </dependency>
         
A jar version might be released in the future.

To use actions in your project import statically the actions you are interested in, in  myLittleActions file.

``` java
import static lu.jimenez.research.mylittleplugin.MyLittleActions.*;
```

Then you can call them when you want within a task :

``` java
        newTask()
                .readGlobalIndexAll("nodes")
                .traverse("children") //formerly get
                .then(traverseOrAttributeInVar("name", "childrenName"))
                .execute(graph, null);
```
