package lu.jimenez.research.mylittleplugin;

import org.junit.jupiter.api.Test;
import org.mwg.Type;
import org.mwg.task.ActionFunction;
import org.mwg.task.TaskContext;

import static lu.jimenez.research.mylittleplugin.MyLittleActions.readUpdatedTimeVar;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mwg.task.Tasks.newTask;

class ActionReadUpdatedTimeVarTest extends ActionTest {

    @Test
    public void test() {
        initGraph();
        final int[] counter = {0};
        newTask()
                .travelInTime("0")
                .createNode()
                .setAttribute("name", Type.STRING, "node")
                .setAttribute("value", Type.INT, "0")

                .travelInTime("2")
                .setAttribute("value", Type.INT, "2")
                .setAsVar("testNode")
                .inject("3")
                .travelInTime("4")
                .readVar("testNode")
                .thenDo(new ActionFunction() {
                    public void eval(TaskContext ctx) {
                        assertEquals(4, ctx.time());
                        assertEquals(2, ctx.resultAsNodes().get(0).time());
                        counter[0]++;
                        ctx.continueTask();
                    }
                })
                .inject("3")
                .travelInTime("1")
                .readVar("testNode")
                .thenDo(new ActionFunction() {
                    public void eval(TaskContext ctx) {
                        assertEquals(1, ctx.time());
                        assertEquals(2, ctx.resultAsNodes().get(0).time());
                        counter[0]++;
                        ctx.continueTask();
                    }
                })
                .then(readUpdatedTimeVar("testNode"))
                .thenDo(new ActionFunction() {
                    public void eval(TaskContext ctx) {
                        assertEquals(1, ctx.time());
                        assertEquals(1, ctx.resultAsNodes().get(0).time());
                        counter[0]++;
                        ctx.continueTask();
                    }
                }).execute(graph, null);
        assertEquals(3, counter[0]);
        removeGraph();
    }
}