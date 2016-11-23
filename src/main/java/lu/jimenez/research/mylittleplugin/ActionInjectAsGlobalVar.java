package lu.jimenez.research.mylittleplugin;

import org.mwg.task.Action;
import org.mwg.task.TaskContext;


public class ActionInjectAsGlobalVar implements Action {

    private final String _variable;
    private final Object _toInject;

    ActionInjectAsGlobalVar(String p_variable, final Object toInject){
        _variable = p_variable;
        _toInject = toInject;
    }

    public void eval(final TaskContext taskContext) {
        taskContext.setGlobalVariable(_variable,_toInject);
        taskContext.continueTask();
    }
}
