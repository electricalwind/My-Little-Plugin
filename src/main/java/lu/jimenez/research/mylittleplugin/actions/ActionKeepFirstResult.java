package lu.jimenez.research.mylittleplugin.actions;

import org.mwg.plugin.AbstractTaskAction;
import org.mwg.task.TaskContext;

public class ActionKeepFirstResult extends AbstractTaskAction {
    public ActionKeepFirstResult() {
        super();
    }

    public void eval(final TaskContext taskContext) {
        taskContext.continueWith(taskContext.wrap(taskContext.result().get(0)));
    }
}
