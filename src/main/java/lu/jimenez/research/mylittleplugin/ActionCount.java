package lu.jimenez.research.mylittleplugin;

import org.mwg.Constants;
import org.mwg.task.Action;
import org.mwg.task.TaskContext;

public class ActionCount implements Action {

    ActionCount(){
        super();
    }

    public void eval(TaskContext taskContext) {
        final int count = taskContext.result().size();
        taskContext.continueWith(taskContext.wrap(count));
    }

    public void serialize(StringBuilder builder) {
        builder.append(MLPActionNames.COUNT);
        builder.append(Constants.TASK_PARAM_OPEN);
        builder.append(Constants.TASK_PARAM_CLOSE);
    }

    @Override
    public String toString(){
        final StringBuilder res = new StringBuilder();
        serialize(res);
        return res.toString();
    }
}
