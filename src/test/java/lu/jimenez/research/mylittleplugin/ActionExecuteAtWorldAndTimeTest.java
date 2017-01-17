package lu.jimenez.research.mylittleplugin;

import org.junit.jupiter.api.Test;
import org.mwg.Callback;
import org.mwg.Node;
import org.mwg.Type;
import org.mwg.task.ActionFunction;
import org.mwg.task.TaskContext;

import static lu.jimenez.research.mylittleplugin.MyLittleActions.executeAtWorldAndTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mwg.core.task.Actions.newTask;

class ActionExecuteAtWorldAndTimeTest extends ActionTest {

    @Test
    public void test() {
        initGraph();
        final int[] counter = {0};
        newTask().travelInTime("10")
                .then(executeAtWorldAndTime("0", "1",
                        newTask()
                                .createNode()
                                .setAttribute("name", Type.STRING, "newNode")
                                .defineAsVar("newNode")
                                .readGlobalIndex("roots")
                                .addVarToRelation("children", "newNode")
                        )
                )
                .thenDo(new ActionFunction() {
                    public void eval(TaskContext ctx) {
                        assertEquals(10, ctx.time());
                        ctx.resultAsNodes().get(0).relation("children", new Callback<Node[]>() {
                            public void on(Node[] result) {
                                assertEquals(3, result.length);
                            }
                        });
                        counter[0]++;
                        ctx.continueTask();
                    }
                })
                .travelInTime("0")
                .readGlobalIndex("roots")
                .traverse("children")
                .thenDo(new ActionFunction() {
                    public void eval(TaskContext ctx) {
                        assertEquals(2, ctx.result().size());
                        counter[0]++;
                        ctx.continueTask();
                    }
                })
                .travelInTime("2")
                .readGlobalIndex("roots")
                .traverse("children")
                .thenDo(new ActionFunction() {
                    public void eval(TaskContext ctx) {
                        assertEquals(3, ctx.result().size());
                        counter[0]++;
                        ctx.continueTask();
                    }
                }).execute(graph,null);

        assertEquals(3,counter[0]);
        removeGraph();
    }

}