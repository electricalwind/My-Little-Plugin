package lu.jimenez.research.mylittleplugin;

import org.junit.jupiter.api.Test;
import org.mwg.task.ActionFunction;
import org.mwg.task.TaskContext;

import static lu.jimenez.research.mylittleplugin.MyLittleActions.increment;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mwg.internal.task.CoreActions.*;
import static org.mwg.task.Tasks.newTask;

class ActionIncrementTest extends ActionTest{

    @Test
    public void test(){
    initGraph();
        final int[] counter ={0};
    newTask()
            .then(inject(1))
            .then(addToVar("inc"))
            .then(increment("inc",1))
            .thenDo(new ActionFunction() {
                public void eval(TaskContext context) {
                    assertEquals(context.variable("inc").get(0),2);
                    counter[0]++;
                    context.continueTask();
                }
            })
            .then(increment("inc",2))
            .thenDo(new ActionFunction() {
                public void eval(TaskContext context) {
                    assertEquals(context.variable("inc").get(0),4);
                    counter[0]++;
                    context.continueTask();
                }
            })
            .execute(graph,null);
        assertEquals(2, counter[0]);
    removeGraph();
    }

}