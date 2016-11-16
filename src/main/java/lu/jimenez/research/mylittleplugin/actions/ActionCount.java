package lu.jimenez.research.mylittleplugin.actions;

import org.mwg.plugin.AbstractTaskAction;
import org.mwg.task.TaskContext;

public class ActionCount extends AbstractTaskAction{

    public ActionCount(){
        super();
    }

    public void eval(TaskContext taskContext) {
        final int count = taskContext.result().size();
        taskContext.continueWith(taskContext.wrap(count));
    }

    @Override
    public String toString(){
        return "count";
    }
}
