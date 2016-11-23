package lu.jimenez.research.mylittleplugin;

import org.mwg.task.Action;
import org.mwg.task.Task;

public class MyLittleActions {


    public static Action count() {
        return new ActionCount();
    }


    public static Action getAsVar(String p_name, String p_variable) {
        return new ActionGetAsVar(p_name, p_variable);
    }

    public static Action getOrCreate(String p_property, byte p_propertyType) {
        return new ActionGetOrCreate(p_property, p_propertyType);
    }

    public static Action ifEmptyThen(final Task then) {

        return new ActionIfEmptyThen(then);
    }

    public static Action ifNotEmptyThen(final Task then) {

        return new ActionIfNotEmptyThen(then);

    }

    public static Action injectAsGlobalVar(final String p_variable, final Object obj) {

        return new ActionInjectAsGlobalVar(p_variable, obj);
    }

    public static Action increment(String p_variable, int p_incrementValue) {
        return new ActionIncrement(p_variable, p_incrementValue);
    }

    public static Action keepFirstResult() {
        return new ActionKeepFirstResult();
    }

}
