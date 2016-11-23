package lu.jimenez.research.mylittleplugin;

import org.mwg.task.Action;
import org.mwg.task.TaskContext;

public class ActionKeepFirstResult implements Action {
    ActionKeepFirstResult() {
        super();
    }

    public void eval(final TaskContext taskContext) {
        taskContext.continueWith(taskContext.wrap(taskContext.result().get(0)));
    }
}
