package lu.jimenez.research.mylittleplugin;

import org.junit.jupiter.api.Test;
import org.mwg.task.ActionFunction;
import org.mwg.task.TaskContext;

import static lu.jimenez.research.mylittleplugin.MyLittleActions.ifNotEmptyThenElse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mwg.internal.task.CoreActions.*;

class ActionIfNotEmptyThenElseTest extends ActionTest {

    @Test
    public void test() {
        initGraph();
        newTask()
                .then(declareVar("myvar"))
                .then(inject("content"))
                .then(ifNotEmptyThenElse(
                        newTask()
                                .then(addToVar("myvar")),
                        newTask()
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