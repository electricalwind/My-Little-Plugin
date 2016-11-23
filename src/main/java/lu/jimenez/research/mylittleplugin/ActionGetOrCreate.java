package lu.jimenez.research.mylittleplugin;

import org.mwg.Callback;
import org.mwg.DeferCounter;
import org.mwg.Node;
import org.mwg.Type;
import org.mwg.base.BaseNode;
import org.mwg.plugin.Job;
import org.mwg.task.Action;
import org.mwg.task.TaskContext;
import org.mwg.task.TaskResult;

public class ActionGetOrCreate implements Action {

    private final String _property;
    private final byte _propertyType;

    ActionGetOrCreate(String p_property, byte p_propertyType) {
        super();
        _property = p_property;
        _propertyType = p_propertyType;
    }

    public void eval(final TaskContext context) {
        final TaskResult finalResult = context.newResult();
        final String flatName = context.template(_property);
        final TaskResult previousResult = context.result();
        if (previousResult != null) {
            final int previousSize = previousResult.size();
            final DeferCounter defer = context.graph().newCounter(previousSize);
            for (int i = 0; i < previousSize; i++) {
                final Object loop = previousResult.get(i);
                if (loop instanceof BaseNode) {
                    final Node casted = (Node) loop;

                    if (_propertyType == Type.RELATION) {
                        casted.relation(flatName, new Callback<Node[]>() {
                            public void on(Node[] result) {
                                if (result != null) {
                                    for (int j = 0; j < result.length; j++) {
                                        finalResult.add(result[j]);
                                    }
                                } else {
                                    casted.getOrCreate(_property, Type.RELATION);
                                }
                                casted.free();
                                defer.count();
                            }
                        });
                    } else {
                        Object resolved = casted.get(flatName);
                        if (resolved != null) {
                            finalResult.add(resolved);
                        } else {
                            finalResult.add(casted.getOrCreate(_property, _propertyType));
                        }
                        casted.free();
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
                    //optimization to avoid iterating again on previous result set
                    previousResult.clear();
                    context.continueWith(finalResult);
                }
            });
        } else {
            context.continueTask();
        }

    }
}
