package lu.jimenez.research.mylittleplugin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mwg.Type;
import org.mwg.task.ActionFunction;
import org.mwg.task.TaskContext;

import static lu.jimenez.research.mylittleplugin.MyLittleActions.checkForFuture;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mwg.task.Tasks.newTask;

class ActionCheckForFutureTest extends ActionTest{

    @BeforeEach
    public void runBeforeTestMethod() {
        initGraph();
        newTask()
                .travelInTime("4")
                .readGlobalIndex("roots")
                .setAttribute("modify", Type.BOOL,"true")
                .execute(graph,null);
    }



    @AfterEach
    public void runAfterTestMethod() {
        removeGraph();
    }



    @Test
    public void testoneNodeWithoutFuture(){
        final boolean[] bool ={false};
        final int[] count = {0};
        newTask()
                .travelInTime("5")
                .readGlobalIndex("roots")
                .then(checkForFuture())
                .thenDo(new ActionFunction() {
                    public void eval(TaskContext ctx) {
                        bool[0] = true;
                        count[0]++;
                        ctx.continueTask();
                    }
                }).execute(graph,null);

        assertEquals(count[0],1);
        assert(bool[0]);
    }

    @Test
    public void testoneNodeWithFuture(){
        final boolean[] bool ={true};
        final int[] count = {0};
        newTask()
                .travelInTime("3")
                .readGlobalIndex("roots")
                .then(checkForFuture())
                .thenDo(new ActionFunction() {
                    public void eval(TaskContext ctx) {
                        bool[0] = false;
                        count[0]++;
                        ctx.continueTask();
                    }
                }).execute(graph, null);

        assertEquals(count[0],0);
        assert(bool[0]);
    }

    @Test
    public void testMixFuture(){
        final boolean[] bool ={true};
        final int[] count = {0};
        newTask()
                .travelInTime("3")
                .readGlobalIndex("nodes")
                .then(checkForFuture())
                .thenDo(new ActionFunction() {
                    public void eval(TaskContext ctx) {
                        bool[0] = false;
                        count[0]++;
                        ctx.continueTask();
                    }
                }).execute(graph, null);

        assertEquals(count[0],0);
        assert(bool[0]);
    }
/**
    @Test
    public void testforkworld(){
        final boolean[] bool ={true};
        final int[] count = {0};
        newTask()
                .travelInTime("6")
                .thenDo(new ActionFunction() {
                    public void eval(TaskContext ctx) {
                        ctx.setVariable("world",ctx.graph().fork(0));
                        ctx.continueTask();
                    }
                })
                .travelInWorld("{{world}}")
                .readGlobalIndex("roots")
                .setAttribute("modify",Type.STRING,"world1")
                .println("{{result}}")
                .travelInWorld("0")
                .travelInTime("5")
                .then(checkForFuture())
                .thenDo(new ActionFunction() {
                    public void eval(TaskContext ctx) {
                        bool[0] = false;
                        count[0]++;
                        ctx.continueTask();
                    }
                }).execute(graph, null);

        assertEquals(count[0],0);
        assert(bool[0]);
    }
*/

}