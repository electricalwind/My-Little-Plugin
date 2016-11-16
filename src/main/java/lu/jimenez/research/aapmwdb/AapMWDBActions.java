package lu.jimenez.research.aapmwdb;

import lu.jimenez.research.aapmwdb.actions.ActionIfEmptyThen;
import lu.jimenez.research.aapmwdb.actions.ActionIfNotEmptyThen;
import org.mwg.task.Action;
import org.mwg.task.Task;
import org.mwg.task.TaskContext;

import static org.mwg.task.Actions.newTask;

public class AapMWDBActions {

    public static final String COUNT = "count";

    public static final String IF_EMPTY_THEN = "ifEmptyThen";

    public static final String IF_NOT_EMPTY_THEN = "ifNotEmptyThen";

    public static final String GET_AS_VAR = "getAsVar";

    public static Task count() {
        return newTask().action(COUNT, "");
    }

    public static Task ifEmptyThen(final Task then) {
        return newTask().then(new Action() {
            public void eval(TaskContext taskContext) {
                new ActionIfEmptyThen(then).eval(taskContext);
            }
        });
    }

    public static Task ifNotEmptyThen(final Task then) {
        return newTask().then(new Action() {
            public void eval(TaskContext taskContext) {
                new ActionIfNotEmptyThen(then).eval(taskContext);
            }
        });
    }

    public static Task getAsVar(String p_name, String p_variable) {
        return newTask().action(GET_AS_VAR, p_name + "," + p_variable);
    }

}
