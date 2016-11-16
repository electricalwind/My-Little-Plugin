package lu.jimenez.research.mylittleplugin.actions;

import org.mwg.Callback;
import org.mwg.DeferCounter;
import org.mwg.Node;
import org.mwg.Type;
import org.mwg.plugin.AbstractNode;
import org.mwg.plugin.AbstractTaskAction;
import org.mwg.plugin.Job;
import org.mwg.task.TaskContext;
import org.mwg.task.TaskResult;

public class ActionGetAsVar extends AbstractTaskAction {

    private final String _name;
    private final String _variable;

    public ActionGetAsVar(final String p_name, final String p_variable) {
        super();
        this._name = p_name;
        this._variable = p_variable;
    }

    public void eval(final TaskContext taskContext) {
        final TaskResult finalResult = taskContext.newResult();
        final String flatName = taskContext.template(_name);
        final TaskResult previousResult = taskContext.result();
        if (previousResult != null) {
            final int previousSize = previousResult.size();
            final DeferCounter defer = taskContext.graph().newCounter(previousSize);
            for (int i = 0; i < previousSize; i++) {
                final Object loop = previousResult.get(i);
                if (loop instanceof AbstractNode) {
                    final Node casted = (Node) loop;
                    if (casted.type(flatName) == Type.RELATION) {
                        casted.rel(flatName, new Callback<Node[]>() {

                            public void on(Node[] result) {
                                if (result != null) {
                                    for (int j = 0; j < result.length; j++) {
                                        finalResult.add(result[j]);
                                    }
                                }
                                defer.count();
                            }
                        });
                    } else {
                        Object resolved = casted.get(flatName);
                        if (resolved != null) {
                            finalResult.add(resolved);
                        }
                        defer.count();
                    }
                } else {
                    //TODO add closable management
                    finalResult.add(loop);
                    defer.count();
                }
            }
            defer.then(new Job() {
                public void run() {
                    taskContext.defineVariable(taskContext.template(_variable),finalResult);

                    taskContext.continueTask();
                }
            });
        } else {
            taskContext.continueTask();
        }
    }

    @Override
    public String toString(){
        return "getAsVar " + _name + " "+ _variable;
    }
}
