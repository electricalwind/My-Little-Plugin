/**
 * Copyright 2017 Matthieu Jimenez.  All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package mylittleplugin;

import greycat.Action;
import greycat.Graph;
import greycat.Task;
import greycat.Type;
import greycat.internal.task.CoreTask;
import greycat.internal.task.TaskHelper;
import greycat.plugin.ActionFactory;
import greycat.plugin.Plugin;

import java.util.Map;

public class MyLittleActionPlugin implements Plugin {


    private static Task getOrCreate(Map<Integer, Task> contextTasks, String param) {
        Integer taskId = TaskHelper.parseInt(param);
        Task previous = contextTasks.get(taskId);
        if (previous == null) {
            previous = new CoreTask();
            contextTasks.put(taskId, previous);
        }
        return previous;
    }

    public void start(Graph graph) {
        //Count
        graph.actionRegistry()
                .declaration(MLPActionNames.COUNT)
                .setParams()
                .setDescription("Count the number of result in the current context and put it as the new result")
                .setFactory(new ActionFactory() {
                                public Action create(Object[] params) {
                                    return MyLittleActions.count();
                                }
                            }

                );


        //Check for future
        graph.actionRegistry()
                .declaration(MLPActionNames.CHECK_FOR_FUTURE)
                .setParams()
                .setDescription("Checks if nodes present in the current context result have modification in the future. If yes an exception is returned.")
                .setFactory(new ActionFactory() {
                    public Action create(Object[] params) {
                        return MyLittleActions.checkForFuture();
                    }
                });


        //Execute At World ANd Time
        graph.actionRegistry()
                .declaration(MLPActionNames.EXECUTE_AT_WORLD_AND_TIME)
                .setParams(Type.STRING, Type.STRING, Type.TASK)
                .setDescription("execute a given task at a given world and time and then come back to the current time")
                .setFactory(new ActionFactory() {
                    public Action create(Object[] params) {
                        return MyLittleActions.executeAtWorldAndTime((String) params[0], (String) params[1], (Task) params[2]);
                    }
                });


        //IF empty then
        graph.actionRegistry()
                .declaration(MLPActionNames.IF_EMPTY_THEN)
                .setParams(Type.TASK)
                .setDescription("launch the given task if the current result is empty")
                .setFactory(new ActionFactory() {
                    public Action create(Object[] params) {
                        return MyLittleActions.ifEmptyThen((Task) params[0]);
                    }
                });


        //If not empty then
        graph.actionRegistry()
                .declaration(MLPActionNames.IF_NOT_EMPTY_THEN)
                .setParams(Type.TASK)
                .setDescription("launch the given task if the current result is not empty")
                .setFactory(new ActionFactory() {
                    public Action create(Object[] params) {
                        return MyLittleActions.ifNotEmptyThen((Task) params[0]);
                    }
                });

        //IF empty then else
        graph.actionRegistry()
                .declaration(MLPActionNames.IF_EMPTY_THEN_ELSE)
                .setParams(Type.TASK, Type.TASK)
                .setDescription("launch the first task if the current result is empty, else launch the second task")
                .setFactory(new ActionFactory() {
                    public Action create(Object[] params) {
                        return MyLittleActions.ifEmptyThenElse((Task) params[0], (Task) params[1]);
                    }
                });

        //If not empty then else
        graph.actionRegistry()
                .declaration(MLPActionNames.IF_NOT_EMPTY_THEN_ELSE)
                .setParams(Type.TASK, Type.TASK)
                .setDescription("launch the first task if the current result is not empty, else launch the second task")
                .setFactory(new ActionFactory() {
                    public Action create(Object[] params) {
                        return MyLittleActions.ifNotEmptyThenElse((Task) params[0], (Task) params[1]);
                    }
                });

        //Increment
        graph.actionRegistry()
                .declaration(MLPActionNames.INCREMENT)
                .setParams(Type.STRING, Type.INT)
                .setDescription("increment the current value of a variable by 1")
                .setFactory(new ActionFactory() {
                    public Action create(Object[] params) {
                        return MyLittleActions.increment((String) params[0], (Integer) params[1]);
                    }
                });


        //Keep First
        graph.actionRegistry()
                .declaration(MLPActionNames.KEEP_FIRST_RESULT)
                .setParams()
                .setDescription("modify the current context result to only keep its first element")
                .setFactory(new ActionFactory() {
                    public Action create(Object[] params) {
                        return MyLittleActions.keepFirstResult();
                    }
                });

        // TraverseOr Attribute
        graph.actionRegistry()
                .declaration(MLPActionNames.TRAVERSE_OR_ATTRIBUTE_IN_VAR)
                .setParams(Type.STRING, Type.STRING, Type.STRING_ARRAY)
                .setDescription("store the result of the traverse in a variable")
                .setFactory(new ActionFactory() {
                    public Action create(Object[] params) {
                        final String[] varargs = (String[]) params[2];
                        if (varargs != null) {
                            return MyLittleActions.traverseOrAttributeInVar((String) params[0], (String) params[1], varargs);
                        } else {
                            return MyLittleActions.traverseOrAttributeInVar((String) params[0], (String) params[1]);
                        }
                    }
                });

        //Flip Vars
        graph.actionRegistry()
                .declaration(MLPActionNames.FLIP_VARS)
                .setParams(Type.STRING, Type.STRING)
                .setDescription("interchange the content of two variable")
                .setFactory(new ActionFactory() {
                    public Action create(Object[] params) {
                        return MyLittleActions.flipVars((String) params[0], (String) params[1]);
                    }
                });


        //Flip Vars
        graph.actionRegistry()
                .declaration(MLPActionNames.FLIP_VAR_AND_RESULT)
                .setParams(Type.STRING)
                .setDescription("interchange the content of a variable with the current result")
                .setFactory(new ActionFactory() {
                    public Action create(Object[] params) {
                        return MyLittleActions.flipVarAndResult((String) params[0]);
                    }
                });

        //Read Updated Time Var
        graph.actionRegistry()
                .declaration(MLPActionNames.READ_UPDATED_TIME_VAR)
                .setParams(Type.STRING)
                .setDescription("Put the content of a var in the current result, if the var contains nodes they are put to the current context time")
                .setFactory(new ActionFactory() {
                    public Action create(Object[] params) {
                        return MyLittleActions.readUpdatedTimeVar((String) params[0]);
                    }
                });

        //Traverse Dedup
        graph.actionRegistry()
                .declaration(MLPActionNames.TRAVERSE_DEDUP)
                .setParams(Type.STRING, Type.STRING_ARRAY)
                .setDescription("action that traverse but only put one occurence of each node")
                .setFactory(new ActionFactory() {
                    public Action create(Object[] params) {
                        final String[] varargs = (String[]) params[2];
                        if (varargs != null) {
                            return MyLittleActions.traverseDedup((String) params[0], varargs);
                        } else {
                            return MyLittleActions.traverseDedup((String) params[0]);
                        }
                    }
                });

    }

    public void stop() {

    }
}
