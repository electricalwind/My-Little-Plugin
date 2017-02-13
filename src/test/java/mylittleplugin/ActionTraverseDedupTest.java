package mylittleplugin;

import greycat.ActionFunction;
import greycat.TaskContext;
import greycat.Type;
import org.junit.jupiter.api.Test;

import static greycat.Tasks.newTask;
import static mylittleplugin.MyLittleActions.readUpdatedTimeVar;
import static mylittleplugin.MyLittleActions.traverseDedup;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ActionTraverseDedupTest extends ActionTest {

    @Test
    public void test() {
        initGraph();
        int[] counter = {0};

        newTask()
                .travelInTime("0")
                .readGlobalIndex("roots")
                .setAsVar("rootNode")
                .declareVar("nodes")
                .loop("1", "100",
                        newTask()
                                .createNode()
                                .setAttribute("name", Type.STRING, "{{i}}")
                                .addToVar("nodes")
                )
                .readVar("nodes")
                .travelInTime("1")
                .addVarToRelation("root", "rootNode")
                .setAsVar("nodes")
                .then(readUpdatedTimeVar("rootNode"))
                .setAsVar("rootNode")
                .addVarToRelation("addedNodes", "nodes")
                .traverse("addedNodes")
                .traverse("root")
                .thenDo(new ActionFunction() {
                    @Override
                    public void eval(TaskContext ctx) {
                        assertEquals(100, ctx.result().size());
                        long rootId = ctx.resultAsNodes().get(0).id();
                        ctx.setVariable("rootId", rootId);
                        assertEquals(rootId, ctx.resultAsNodes().get(99).id());
                        counter[0]++;
                        ctx.continueTask();
                    }
                })
                .then(readUpdatedTimeVar("nodes"))
                .then(traverseDedup("root"))
                .thenDo(new ActionFunction() {
                    @Override
                    public void eval(TaskContext ctx) {
                        assertEquals(1, ctx.result().size());
                        assertEquals(ctx.variable("rootId").get(0), ctx.resultAsNodes().get(0).id());
                        counter[0]++;
                        ctx.continueTask();
                    }
                })

                .travelInTime("0")
                .then(readUpdatedTimeVar("nodes"))
                .then(traverseDedup("root"))
                .thenDo(new ActionFunction() {
                    @Override
                    public void eval(TaskContext ctx) {
                        assertEquals(0, ctx.result().size());
                        counter[0]++;
                        ctx.continueTask();
                    }
                })


                .println("everything done :)")
                .execute(graph, null);
        assertEquals(3, counter[0]);
        removeGraph();
    }


    @Test
    public void testIndex() {
        initGraph();
        int[] counter = {0};

        newTask()
                .travelInTime("0")
                .readGlobalIndex("roots")
                .setAsVar("rootNode")
                .declareVar("nodes")
                .loop("1", "100",
                        newTask()
                                .createNode()
                                .setAttribute("name", Type.STRING, "{{i}}")
                                .addToVar("nodes")
                )
                .readVar("nodes")
                .travelInTime("1")
                .addVarToRelation("root", "rootNode", "name")
                .setAsVar("nodes")
                .then(readUpdatedTimeVar("rootNode"))
                .setAsVar("rootNode")
                .addVarToRelation("addedNodes", "nodes", "name")
                .traverse("addedNodes")
                .traverse("root")
                .thenDo(new ActionFunction() {
                    @Override
                    public void eval(TaskContext ctx) {
                        assertEquals(100, ctx.result().size());
                        long rootId = ctx.resultAsNodes().get(0).id();
                        ctx.setVariable("rootId", rootId);
                        assertEquals(rootId, ctx.resultAsNodes().get(99).id());
                        counter[0]++;
                        ctx.continueTask();
                    }
                })
                .then(readUpdatedTimeVar("nodes"))
                .then(traverseDedup("root"))
                .thenDo(new ActionFunction() {
                    @Override
                    public void eval(TaskContext ctx) {
                        assertEquals(1, ctx.result().size());
                        assertEquals(ctx.variable("rootId").get(0), ctx.resultAsNodes().get(0).id());
                        counter[0]++;
                        ctx.continueTask();
                    }
                })

                .travelInTime("0")
                .then(readUpdatedTimeVar("nodes"))
                .then(traverseDedup("root"))
                .thenDo(new ActionFunction() {
                    @Override
                    public void eval(TaskContext ctx) {
                        assertEquals(0, ctx.result().size());
                        counter[0]++;
                        ctx.continueTask();
                    }
                })
                .println("everything done :)")
                .execute(graph, null);
        assertEquals(3, counter[0]);
        removeGraph();
    }

    @Test
    public void testIndex2() {
        initGraph();
        int[] counter = {0};

        newTask()
                .travelInTime("0")
                .readGlobalIndex("roots")
                .setAsVar("rootNode")
                .declareVar("nodes")
                .loop("1", "3",
                        newTask()
                                .createNode()
                                .setAttribute("name", Type.STRING, "{{i}}")
                                .addToVar("nodes")
                )
                .readVar("nodes")
                .travelInTime("1")
                .addVarToRelation("root", "rootNode", "name")
                .setAsVar("nodes")
                .then(readUpdatedTimeVar("rootNode"))
                .setAsVar("rootNode")
                .addVarToRelation("addedNodes", "nodes", "name")
                .traverse("addedNodes")
                .traverse("root")
                .thenDo(new ActionFunction() {
                    @Override
                    public void eval(TaskContext ctx) {
                        assertEquals(3, ctx.result().size());
                        long rootId = ctx.resultAsNodes().get(0).id();
                        ctx.setVariable("rootId", rootId);
                        assertEquals(rootId, ctx.resultAsNodes().get(2).id());
                        counter[0]++;
                        ctx.continueTask();
                    }
                })
                .then(readUpdatedTimeVar("nodes"))
                .then(traverseDedup("root", "name", "root"))
                .thenDo(new ActionFunction() {
                    @Override
                    public void eval(TaskContext ctx) {
                        assertEquals(1, ctx.result().size());
                        assertEquals(ctx.variable("rootId").get(0), ctx.resultAsNodes().get(0).id());
                        counter[0]++;
                        ctx.continueTask();
                    }
                })

                .travelInTime("0")
                .then(readUpdatedTimeVar("nodes"))
                .then(traverseDedup("root"))
                .thenDo(new ActionFunction() {
                    @Override
                    public void eval(TaskContext ctx) {
                        assertEquals(0, ctx.result().size());
                        counter[0]++;
                        ctx.continueTask();
                    }
                })

                .println("everything done :)")
                .execute(graph, null);
        assertEquals(3, counter[0]);
        removeGraph();
    }

}