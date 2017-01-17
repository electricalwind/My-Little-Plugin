package lu.jimenez.research.mylittleplugin;

import org.mwg.task.Action;
import org.mwg.task.Task;

public class MyLittleActions {


    public static Action count() {
        return new ActionCount();
    }

    public static Action checkForFuture() { return new ActionCheckForFuture();}

    public static Action traverseOrAttributeInVar(final String p_name, final String p_variable, final String... p_params) {
        return new ActionTraverseOrAttributeInVar(p_name, p_variable, p_params);
    }

    public static Action ifEmptyThen(final Task then) {
        return new ActionIfEmptyThen(then);
    }

    public static Action ifNotEmptyThen(final Task then) {
        return new ActionIfNotEmptyThen(then);
    }

    public static Action ifEmptyThenElse(final Task then, final Task _else) {
        return new ActionIfEmptyThenElse(then, _else);
    }

    public static Action ifNotEmptyThenElse(final Task then, final Task _else) {
        return new ActionIfNotEmptyThenElse(then, _else);
    }

    public static Action injectAsVar(final String p_variable, final Object obj) {
        return new ActionInjectAsVar(p_variable, obj);
    }

    public static Action increment(final String p_variable, final int p_incrementValue) {
        return new ActionIncrement(p_variable, p_incrementValue);
    }

    public static Action keepFirstResult() {
        return new ActionKeepFirstResult();
    }

    public static Action flipVars(final String var1, final String var2) {
        return new ActionFlipVars(var1,var2);
    }

    public static Action flipVarAndResult(final String var) {
        return new ActionFlipVarAndResult(var);
    }

}
