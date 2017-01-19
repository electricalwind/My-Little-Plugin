package lu.jimenez.research.mylittleplugin;

import org.mwg.Graph;
import org.mwg.Type;
import org.mwg.internal.task.CoreTask;
import org.mwg.internal.task.TaskHelper;
import org.mwg.plugin.ActionFactory;
import org.mwg.plugin.Plugin;
import org.mwg.task.Action;
import org.mwg.task.Task;

import java.util.Map;

import static lu.jimenez.research.mylittleplugin.MyLittleActions.*;

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
                                    return count();
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
                        return checkForFuture();
                    }
                });


        //Execute At World ANd Time
        graph.actionRegistry()
                .declaration(MLPActionNames.EXECUTE_AT_WORLD_AND_TIME)
                .setParams(Type.STRING, Type.STRING, Type.TASK)
                .setDescription("execute a given task at a given world and time and then come back to the current time")
                .setFactory(new ActionFactory() {
                    public Action create(Object[] params) {
                        return executeAtWorldAndTime((String) params[0], (String) params[1], (Task) params[2]);
                    }
                });


        //IF empty then
        graph.actionRegistry()
                .declaration(MLPActionNames.IF_EMPTY_THEN)
                .setParams(Type.TASK)
                .setDescription("launch the given task if the current result is empty")
                .setFactory(new ActionFactory() {
                    public Action create(Object[] params) {
                        return ifEmptyThen((Task) params[0]);
                    }
                });


        //If not empty then
        graph.actionRegistry()
                .declaration(MLPActionNames.IF_NOT_EMPTY_THEN)
                .setParams(Type.TASK)
                .setDescription("launch the given task if the current result is not empty")
                .setFactory(new ActionFactory() {
                    public Action create(Object[] params) {
                        return ifNotEmptyThen((Task) params[0]);
                    }
                });

        //IF empty then else
        graph.actionRegistry()
                .declaration(MLPActionNames.IF_EMPTY_THEN_ELSE)
                .setParams(Type.TASK, Type.TASK)
                .setDescription("launch the first task if the current result is empty, else launch the second task")
                .setFactory(new ActionFactory() {
                    public Action create(Object[] params) {
                        return ifEmptyThenElse((Task) params[0], (Task) params[1]);
                    }
                });

        //If not empty then else
        graph.actionRegistry()
                .declaration(MLPActionNames.IF_NOT_EMPTY_THEN_ELSE)
                .setParams(Type.TASK, Type.TASK)
                .setDescription("launch the first task if the current result is not empty, else launch the second task")
                .setFactory(new ActionFactory() {
                    public Action create(Object[] params) {
                        return ifNotEmptyThenElse((Task) params[0], (Task) params[1]);
                    }
                });

        //Increment
        graph.actionRegistry()
                .declaration(MLPActionNames.INCREMENT)
                .setParams(Type.STRING, Type.INT)
                .setDescription("increment the current value of a variable by 1")
                .setFactory(new ActionFactory() {
                    public Action create(Object[] params) {
                        return increment((String) params[0], (Integer) params[1]);
                    }
                });


        //Keep First
        graph.actionRegistry()
                .declaration(MLPActionNames.KEEP_FIRST_RESULT)
                .setParams()
                .setDescription("modify the current context result to only keep its first element")
                .setFactory(new ActionFactory() {
                    public Action create(Object[] params) {
                        return keepFirstResult();
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
                            return traverseOrAttributeInVar((String) params[0], (String) params[1], varargs);
                        } else {
                            return traverseOrAttributeInVar((String) params[0], (String) params[1]);
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
                        return flipVars((String) params[0], (String) params[1]);
                    }
                });


        //Flip Vars
        graph.actionRegistry()
                .declaration(MLPActionNames.FLIP_VAR_AND_RESULT)
                .setParams(Type.STRING)
                .setDescription("interchange the content of a variable with the current result")
                .setFactory(new ActionFactory() {
                    public Action create(Object[] params) {
                        return flipVarAndResult((String) params[0]);
                    }
                });

    }

    public void stop() {

    }
}
