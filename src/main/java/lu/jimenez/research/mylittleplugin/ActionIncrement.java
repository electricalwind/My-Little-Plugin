package lu.jimenez.research.mylittleplugin;

import org.mwg.task.Action;
import org.mwg.task.TaskContext;

public class ActionIncrement implements Action {

    private final String _variable;
    private final int _increment;

    ActionIncrement(String p_variable, int p_increment) {
        super();
        _variable = p_variable;
        _increment = p_increment;
    }

    public void eval(final TaskContext taskContext) {
        int currentValue = (Integer) taskContext.variable(_variable).get(0);
        taskContext.setVariable(_variable, currentValue + _increment);
        taskContext.continueTask();
    }

    @Override
    public String toString(){
        return "incrementing var "+_variable+" by "+_increment;
    }
}
