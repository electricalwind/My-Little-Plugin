package lu.jimenez.research.mylittleplugin;

import org.junit.jupiter.api.Test;
import org.mwg.task.ActionFunction;
import org.mwg.task.TaskContext;

import static lu.jimenez.research.mylittleplugin.MyLittleActions.ifEmptyThen;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mwg.internal.task.CoreActions.*;
import static org.mwg.task.Tasks.newTask;

class ActionIfEmptyThenTest extends ActionTest{

    @Test
    public void test(){
        initGraph();
        final int[] counter ={0};
        newTask()
                .then(declareVar("myvar"))
                .then(ifEmptyThen(
                        newTask()
                                .then(inject("content"))
                                .then(addToVar("myvar"))
                        )
                )
                .then(readVar("myvar"))
                .thenDo(new ActionFunction() {
                    public void eval(TaskContext context) {
                        assertEquals(context.result().get(0), "content");
                        counter[0]++;
                        context.continueTask();
                    }
                }).execute(graph,null);
        assertEquals(1, counter[0]);
        removeGraph();
    }



}