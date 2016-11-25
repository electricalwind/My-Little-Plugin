package lu.jimenez.research.mylittleplugin;

import org.junit.jupiter.api.Test;
import org.mwg.task.ActionFunction;
import org.mwg.task.TaskContext;

import static lu.jimenez.research.mylittleplugin.MyLittleActions.keepFirstResult;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mwg.core.task.Actions.get;
import static org.mwg.core.task.Actions.readGlobalIndexAll;
import static org.mwg.core.task.Actions.task;

class ActionKeepFirstResultTest extends ActionTest {

    @Test
    public void test() {
        initGraph();
        task().then(readGlobalIndexAll("nodes"))
                .then(get("children"))
                .thenDo(new ActionFunction() {
                    public void eval(TaskContext context) {
                        assertEquals(context.result().size(),2);
                    }
                })
                .then(keepFirstResult())
                .thenDo(new ActionFunction() {
            public void eval(TaskContext context) {
                assertEquals(context.result().size(),1);
            }
        }).execute(graph,null);
        removeGraph();
    }

}