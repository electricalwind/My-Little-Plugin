package lu.jimenez.research.mylittleplugin;

import org.mwg.Callback;
import org.mwg.plugin.SchedulerAffinity;
import org.mwg.task.Action;
import org.mwg.task.Task;
import org.mwg.task.TaskContext;
import org.mwg.task.TaskResult;

public class ActionIfNotEmptyThen implements Action {

    private org.mwg.task.Task _action;

    ActionIfNotEmptyThen(final Task action) {
        super();
        this._action = action;
    }


    public void eval(final TaskContext taskContext) {
        if (taskContext.result().size() != 0) {
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

