package lu.jimenez.research.mylittleplugin;

import org.junit.jupiter.api.Test;
import org.mwg.task.ActionFunction;
import org.mwg.task.TaskContext;

import static lu.jimenez.research.mylittleplugin.MyLittleActions.injectAsVar;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mwg.task.Tasks.newTask;

class ActionInjectAsVarTest extends ActionTest {

    @Test
    public void test() {
        initGraph();
        final int[] counter ={0};
        newTask()
                .then(injectAsVar("myvar", 1))
                .thenDo(new ActionFunction() {
                    public void eval(TaskContext context) {
                        assertEquals(context.variable("myvar").get(0), 1);
                        counter[0]++;
                        context.continueTask();
                    }
                })
                .execute(graph, null);
        assertEquals(1, counter[0]);
        removeGraph();
    }

}