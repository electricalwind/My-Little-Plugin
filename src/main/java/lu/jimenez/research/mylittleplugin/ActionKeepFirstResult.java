package lu.jimenez.research.mylittleplugin;

import org.mwg.Constants;
import org.mwg.task.Action;
import org.mwg.task.TaskContext;

public class ActionKeepFirstResult implements Action {
    ActionKeepFirstResult() {
        super();
    }

    public void eval(final TaskContext taskContext) {
        if (taskContext.result().size() > 0)
            taskContext.continueWith(taskContext.wrap(taskContext.result().get(0)));
        else taskContext.continueTask();
    }

    public void serialize(StringBuilder builder) {
        builder.append(MLPActionNames.KEEP_FIRST_RESULT);
        builder.append(Constants.TASK_PARAM_OPEN);
        builder.append(Constants.TASK_PARAM_CLOSE);
    }

    @Override
    public String toString() {
        final StringBuilder res = new StringBuilder();
        serialize(res);
        return res.toString();
    }
}
