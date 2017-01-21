package lu.jimenez.research.mylittleplugin;

import org.junit.jupiter.api.Test;
import org.mwg.task.ActionFunction;
import org.mwg.task.TaskContext;

import static lu.jimenez.research.mylittleplugin.MyLittleActions.count;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mwg.internal.task.CoreActions.readGlobalIndex;
import static org.mwg.task.Tasks.newTask;

class ActionCountTest extends ActionTest {

    @Test
    public void testEmpty() {
        initGraph();
        final int[] counter = {0};
        newTask()
                .then(count())
                .thenDo(new ActionFunction() {
                    public void eval(TaskContext context) {

                        assertEquals(context.result().get(0), 0);
                        counter[0]++;
                        context.continueTask();
                    }
                })
                .execute(graph, null);
        assertEquals(1, counter[0]);
        removeGraph();
    }

    @Test
    public void testComplex() {
        initGraph();
        final int[] counter = {0};
        newTask()
                .then(readGlobalIndex("nodes"))
                .then(count())
                .thenDo(new ActionFunction() {
                    public void eval(TaskContext context) {

                        assertEquals(context.result().get(0), 3);
                        counter[0]++;
                        context.continueTask();
                    }
                })
                .execute(graph, null);
        assertEquals(1, counter[0]);
        removeGraph();
    }


}