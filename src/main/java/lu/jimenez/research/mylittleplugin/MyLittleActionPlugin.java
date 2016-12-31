package lu.jimenez.research.mylittleplugin;

import org.mwg.base.BasePlugin;
import org.mwg.core.task.CoreTask;
import org.mwg.core.task.TaskHelper;
import org.mwg.task.Action;
import org.mwg.task.Task;
import org.mwg.task.TaskActionFactory;

import java.util.Map;

import static lu.jimenez.research.mylittleplugin.MyLittleActions.*;

public class MyLittleActionPlugin extends BasePlugin {

    public MyLittleActionPlugin() {

        //Count
        declareTaskAction(MLPActionNames.COUNT, new TaskActionFactory() {
            public Action create(String[] params, Map<Integer, Task> contextTasks) {
                return count();
            }
        });

        //IF empty then
        declareTaskAction(MLPActionNames.IF_EMPTY_THEN, new TaskActionFactory() {
            public Action create(String[] params, Map<Integer, Task> contextTasks) {
                if (params.length != 1) {
                    throw new RuntimeException(MLPActionNames.IF_EMPTY_THEN + " action needs one parameters. Received:" + params.length);
                }
                final Task taskThen = getOrCreate(contextTasks, params[0]);
                return ifEmptyThen(taskThen);
            }
        });

        //If not empty then
        declareTaskAction(MLPActionNames.IF_NOT_EMPTY_THEN, new TaskActionFactory() {
            public Action create(String[] params, Map<Integer, Task> contextTasks) {
                if (params.length != 1) {
                    throw new RuntimeException(MLPActionNames.IF_NOT_EMPTY_THEN + " action needs one parameters. Received:" + params.length);
                }
                final Task taskThen = getOrCreate(contextTasks, params[0]);
                return  ifNotEmptyThen(taskThen);
            }
        });

        //Increment
        declareTaskAction(MLPActionNames.INCREMENT, new TaskActionFactory() {
            public Action create(String[] params, Map<Integer, Task> contextTasks) {
                if (params.length != 2) {
                    throw new RuntimeException(MLPActionNames.INCREMENT + " action needs two parameters. Received:" + params.length);
                }
                try {
                    int incre = Integer.parseInt(params[1]);
                    return increment(params[0], incre);
                } catch (NumberFormatException excep) {
                    throw new RuntimeException(MLPActionNames.INCREMENT + " action needs a parsable integer as second argument. Received:" + params[1]);
                }

            }
        });

        //Keep First
        declareTaskAction(MLPActionNames.KEEP_FIRST_RESULT, new TaskActionFactory() {
            public Action create(String[] params, Map<Integer, Task> contextTasks) {
                return keepFirstResult();
            }
        });

        // TraverseOr Attribute
        declareTaskAction(MLPActionNames.TRAVERSE_OR_ATTRIBUTE_IN_VAR, new TaskActionFactory() {
            public Action create(String[] params, Map<Integer, Task> contextTasks) {
                if (params.length < 2) {
                    throw new RuntimeException(MLPActionNames.TRAVERSE_OR_ATTRIBUTE_IN_VAR + " action needs at least two parameters. Received:" + params.length);
                }
                final String[] getParams = new String[params.length - 2];
                if (params.length > 2) {
                    System.arraycopy(params, 2, getParams, 0, params.length - 2);
                }
                return traverseOrAttributeInVar(params[0], params[1], getParams);
            }
        });
    }


    private static Task getOrCreate(Map<Integer, Task> contextTasks, String param) {
        Integer taskId = TaskHelper.parseInt(param);
        Task previous = contextTasks.get(taskId);
        if (previous == null) {
            previous = new CoreTask();
            contextTasks.put(taskId, previous);
        }
        return previous;
    }

}
