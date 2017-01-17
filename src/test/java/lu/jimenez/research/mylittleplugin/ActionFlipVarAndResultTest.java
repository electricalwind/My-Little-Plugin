package lu.jimenez.research.mylittleplugin;

import org.junit.jupiter.api.Test;
import org.mwg.task.ActionFunction;
import org.mwg.task.TaskContext;
import org.mwg.utility.VerboseHook;

import static lu.jimenez.research.mylittleplugin.MyLittleActions.flipVarAndResult;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mwg.task.Tasks.newTask;

class ActionFlipVarAndResultTest extends ActionTest {


    @Test
    public void testRes() {
        initGraph();
        newTask()
                .inject(1)
                .defineAsVar("var1")
                .inject(2)
                .then(flipVarAndResult("var1"))
                .thenDo(new ActionFunction() {
                    public void eval(TaskContext context) {
                        assertEquals(context.variable("var1").get(0), 2);
                        assertEquals(context.result().get(0), 1);
                    }
                }).addHook(new VerboseHook())
                .execute(graph, null);
        removeGraph();
    }
}