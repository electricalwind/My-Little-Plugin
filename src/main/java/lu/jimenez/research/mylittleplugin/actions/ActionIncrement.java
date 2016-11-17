package lu.jimenez.research.mylittleplugin.actions;

import org.mwg.plugin.AbstractTaskAction;
import org.mwg.task.TaskContext;

public class ActionIncrement extends AbstractTaskAction {

    private final String _variable;
    private final int _increment;

    public ActionIncrement(String p_variable, String p_increment) {
        super();
        _variable = p_variable;
        _increment = Integer.decode(p_increment);
    }

    public void eval(final TaskContext taskContext) {
        int currentValue = (Integer) taskContext.variable(_variable).get(0);
        taskContext.setVariable(_variable, currentValue + _increment);
        taskContext.continueTask();
    }
}
