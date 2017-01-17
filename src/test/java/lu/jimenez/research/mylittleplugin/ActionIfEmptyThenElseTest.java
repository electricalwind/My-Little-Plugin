package lu.jimenez.research.mylittleplugin;

import org.junit.jupiter.api.Test;
import org.mwg.task.ActionFunction;
import org.mwg.task.TaskContext;

import static lu.jimenez.research.mylittleplugin.MyLittleActions.ifEmptyThenElse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mwg.internal.task.CoreActions.*;

class ActionIfEmptyThenElseTest extends ActionTest {

    @Test
    public void test() {
        initGraph();
        newTask()
                .then(declareVar("myvar"))
                .then(ifEmptyThenElse(
                        newTask()
                                .then(inject("content"))
                                .then(addToVar("myvar"))
                        , newTask()
                        )
                )
                .then(readVar("myvar"))
                .thenDo(new ActionFunction() {
                    public void eval(TaskContext context) {
                        assertEquals(context.result().get(0), "content");
                    }
                }).execute(graph, null);
        removeGraph();
    }


}