package lu.jimenez.research.mylittleplugin.actions;

import org.mwg.plugin.AbstractTaskAction;
import org.mwg.task.TaskContext;


public class ActionInjectAsVar extends AbstractTaskAction {
    private final String _variable;
    private final Object _toInject;

    public ActionInjectAsVar(String p_variable, Object toInject){
        _variable = p_variable;
        _toInject = toInject;
    }

    public void eval(TaskContext taskContext) {
        taskContext.setVariable(_variable,_toInject);
        taskContext.continueTask();
    }
}
