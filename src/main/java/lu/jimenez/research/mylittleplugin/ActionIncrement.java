package lu.jimenez.research.mylittleplugin;

import org.mwg.Constants;
import org.mwg.internal.task.TaskHelper;
import org.mwg.task.Action;
import org.mwg.task.TaskContext;

public class ActionIncrement implements Action {

    private final String _variable;
    private final int _increment;

    ActionIncrement(final String p_variable, final int p_increment) {
        super();
        _variable = p_variable;
        _increment = p_increment;
    }

    public void eval(final TaskContext taskContext) {
        int currentValue = (Integer) taskContext.variable(_variable).get(0);
        taskContext.setVariable(_variable, currentValue + _increment);
        taskContext.continueTask();
    }

    public void serialize(StringBuilder builder) {
        builder.append(MLPActionNames.COUNT);
        builder.append(Constants.TASK_PARAM_OPEN);
        TaskHelper.serializeString(_variable, builder,false);
        builder.append(Constants.TASK_PARAM_SEP);
        TaskHelper.serializeString(Integer.toString(_increment), builder,false);
        builder.append(Constants.TASK_PARAM_CLOSE);
    }

    @Override
    public String toString() {
        final StringBuilder res = new StringBuilder();
        serialize(res);
        return res.toString();
    }
}
