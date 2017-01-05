package lu.jimenez.research.mylittleplugin;

import org.mwg.task.Action;
import org.mwg.task.TaskContext;


public class ActionInjectAsVar implements Action {

    private final String _variable;
    private final Object _toInject;

    ActionInjectAsVar(final String p_variable, final Object toInject){
        _variable = p_variable;
        _toInject = toInject;
    }

    public void eval(final TaskContext taskContext) {
        taskContext.defineVariable(_variable,_toInject);
        taskContext.continueTask();
    }

    public void serialize(StringBuilder builder) {
        throw new RuntimeException("inject as var remote action not managed yet!");
    }

    @Override
    public String toString(){
        return "inject("+_toInject.toString()+","+_variable+")";
    }
}
