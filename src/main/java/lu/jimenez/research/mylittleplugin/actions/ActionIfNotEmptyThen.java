package lu.jimenez.research.mylittleplugin.actions;

import org.mwg.Callback;
import org.mwg.plugin.AbstractTaskAction;
import org.mwg.plugin.SchedulerAffinity;
import org.mwg.task.Task;
import org.mwg.task.TaskContext;
import org.mwg.task.TaskResult;

public class ActionIfNotEmptyThen extends AbstractTaskAction {

    private org.mwg.task.Task _action;

    public ActionIfNotEmptyThen(final Task action) {
        super();
        this._action = action;
    }

    @Override
    public void eval(final TaskContext taskContext) {
        if (taskContext.result().size() == 0) {
            _action.executeFrom(taskContext, taskContext.result(), SchedulerAffinity.SAME_THREAD, new Callback<TaskResult>() {
                public void on(TaskResult res) {
                    taskContext.continueWith(res);
                }
            });
        } else taskContext.continueTask();
    }

    @Override
    public String toString() {
        return "ifEmptyThen()";
    }
}

