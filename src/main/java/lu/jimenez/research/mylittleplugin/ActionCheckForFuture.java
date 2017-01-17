package lu.jimenez.research.mylittleplugin;

import org.mwg.Callback;
import org.mwg.Constants;
import org.mwg.Node;
import org.mwg.task.Action;
import org.mwg.task.TaskContext;
import org.mwg.task.TaskResultIterator;

import java.util.ArrayList;
import java.util.List;

import static org.mwg.Constants.END_OF_TIME;

public class ActionCheckForFuture implements Action {

    public void eval(final TaskContext ctx) {
        Long time = ctx.time();
        Object node;
        final boolean[] bool = {true};
        final List<Long> nodeIds = new ArrayList<Long>();
        TaskResultIterator it = ctx.resultAsNodes().iterator();
        while ((node = it.next()) != null) {
            final Node node1 = (Node) node;
            node1.timepoints(
                    time+1, END_OF_TIME, new Callback<long[]>() {
                        public void on(long[] result) {
                            if(result.length !=0){
                                bool[0]=false;
                                nodeIds.add(node1.id());
                            }
                        }
                    }
            );
        }
        if(bool[0]){
            ctx.continueTask();
        }else{
            ctx.endTask(ctx.result(),new RuntimeException("Trying to modify the past of Node(s): "+nodeIds ));
        }
    }

    public void serialize(StringBuilder builder) {
        builder.append(MLPActionNames.CHECK_FOR_FUTURE);
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
