package lu.jimenez.research.mylittleplugin;

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

    @Override
    public String toString(){
        return "count";
    }
}
