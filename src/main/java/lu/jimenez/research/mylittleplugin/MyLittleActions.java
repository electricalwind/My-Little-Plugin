package lu.jimenez.research.mylittleplugin;

import org.mwg.task.Action;
import org.mwg.task.Task;

public class MyLittleActions {


    public static Action count() {
        return new ActionCount();
    }

    public static Action storeGetAsVAr(String p_name, String p_variable,String... p_params) {
        return new ActionStoreGetInVar(p_name, p_variable,p_params);
    }

    public static Action ifEmptyThen(final Task then) {
        return new ActionIfEmptyThen(then);
    }

    public static Action ifNotEmptyThen(final Task then) {
        return new ActionIfNotEmptyThen(then);
    }

    public static Action injectAsGlobalVar(final String p_variable, final Object obj) {
        return new ActionInjectAsVar(p_variable, obj);
    }

    public static Action increment(String p_variable, int p_incrementValue) {
        return new ActionIncrement(p_variable, p_incrementValue);
    }

    public static Action keepFirstResult() {
        return new ActionKeepFirstResult();
    }

}
