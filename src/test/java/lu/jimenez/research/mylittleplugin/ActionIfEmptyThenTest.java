package lu.jimenez.research.mylittleplugin;

import org.junit.jupiter.api.Test;
import org.mwg.task.ActionFunction;
import org.mwg.task.TaskContext;

import static lu.jimenez.research.mylittleplugin.MyLittleActions.ifEmptyThen;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mwg.core.task.Actions.*;

class ActionIfEmptyThenTest extends ActionTest{

    @Test
    public void test(){
        initGraph();
        task()
                .then(declareVar("myvar"))
                .then(ifEmptyThen(
                        task()
                                .then(inject("content"))
                                .then(addToVar("myvar"))
                        )
                )
                .then(readVar("myvar"))
                .thenDo(new ActionFunction() {
                    public void eval(TaskContext context) {
                        assertEquals(context.result().get(0), "content");
                    }
                }).execute(graph,null);
        removeGraph();
    }

}