package lu.jimenez.research.mylittleplugin;

import lu.jimenez.research.mylittleplugin.actions.ActionIfEmptyThen;
import lu.jimenez.research.mylittleplugin.actions.ActionIfNotEmptyThen;
import lu.jimenez.research.mylittleplugin.actions.ActionInjectAsGlobalVar;
import org.mwg.task.Action;
import org.mwg.task.Task;
import org.mwg.task.TaskContext;

import static org.mwg.task.Actions.newTask;

public class MyLittleActions {

    public static final String COUNT = "count";

    public static final String GET_AS_VAR = "getAsVar";

    public static final String GET_OR_CREATE = "getOrCreate";

    public static final String IF_EMPTY_THEN = "ifEmptyThen";

    public static final String IF_NOT_EMPTY_THEN = "ifNotEmptyThen";

    public static final String INCREMENT = "increment";

    public static final String KEEP_FIRST_RESULT = "keepFirst";


    public static Task count() {
        return newTask().action(COUNT, "");
    }


    public static Task getAsVar(String p_name, String p_variable) {
        return newTask().action(GET_AS_VAR, p_name + "," + p_variable);
    }

    public static Task getOrCreate(String p_property, byte p_propertyType) {
        return newTask().action(GET_OR_CREATE, p_property + "," + p_propertyType);
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

    public static Task injectAsGlobalVar(final String p_variable, final Object obj) {
        return newTask().then(new Action() {
            public void eval(TaskContext taskContext) {
                new ActionInjectAsGlobalVar(p_variable, obj);
            }
        });
    }

    public static Task increment(String p_variable, int p_incrementValue) {
        return newTask().action(INCREMENT, p_variable + "," + p_incrementValue);
    }

    public static Task keepFirstResult() {
        return newTask().action(KEEP_FIRST_RESULT, "");
    }

}
