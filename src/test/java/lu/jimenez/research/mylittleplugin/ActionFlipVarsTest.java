package lu.jimenez.research.mylittleplugin;

import org.junit.jupiter.api.Test;
import org.mwg.task.ActionFunction;
import org.mwg.task.TaskContext;

import static lu.jimenez.research.mylittleplugin.MyLittleActions.flipVars;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mwg.task.Tasks.newTask;

class ActionFlipVarsTest extends ActionTest{

    @Test
    public void test(){
        initGraph();
        final int[] counter ={0};
        newTask()
                .inject(1)
                .defineAsVar("var1")
                .inject(2)
                .defineAsVar("var2")
                .then(flipVars("var1","var2"))
                .thenDo(new ActionFunction() {
                    public void eval(TaskContext context) {
                        assertEquals(context.variable("var1").get(0),2);
                        assertEquals(context.variable("var2").get(0),1);
                        counter[0]++;
                        context.continueTask();
                    }
                })
                .execute(graph,null);
        assertEquals(1, counter[0]);
        removeGraph();
    }

}