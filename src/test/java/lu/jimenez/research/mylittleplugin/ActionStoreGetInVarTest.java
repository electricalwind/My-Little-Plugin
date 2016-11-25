package lu.jimenez.research.mylittleplugin;

import org.junit.jupiter.api.Test;
import org.mwg.task.ActionFunction;
import org.mwg.task.TaskContext;

import static lu.jimenez.research.mylittleplugin.MyLittleActions.getAsVar;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mwg.core.task.Actions.*;

class ActionStoreGetInVarTest extends ActionTest {

    @Test
    public void test() {
        initGraph();
        task()
                .then(readGlobalIndexAll("nodes"))
                .then(get("children"))
                .then(getAsVar("name", "childrenName"))
                .thenDo(new ActionFunction() {
                            public void eval(TaskContext context) {
                                assertEquals(context.variable("childrenName").get(0), "n0");
                                assertEquals(context.variable("childrenName").get(1), "n1");
                            }
                        }
                )
                //.hook(new VerboseHookFactory())
                .execute(graph, null);
        removeGraph();
    }

    @Test
    public void testComplex() {
        initGraphR();
        task()
                .then(readGlobalIndexAll("nodes"))
                .then(getAsVar("children", "children"))
                .then(readVar("children"))
                .then(get("name"))
                .thenDo(new ActionFunction() {
                            public void eval(TaskContext context) {
                                assertEquals(context.variable("childrenName").get(0), "n0");
                                assertEquals(context.variable("childrenName").get(1), "n1");
                            }
                        }
                )
                //.hook(new VerboseHookFactory())
                .execute(graph, null);
        removeGraph();
    }

    @Test
    public void testComplex2() {
        initGraphR();
        task()
                .then(readGlobalIndexAll("nodes"))
                .then(getAsVar("children", "children", "name","n0"))
                .then(readVar("children"))
                .then(get("name"))
                .thenDo(new ActionFunction() {
                            public void eval(TaskContext context) {
                                assertEquals(context.result().size(), 1);
                                assertEquals(context.result().get(0), "n0");
                            }
                        }
                )
                .execute(graph, null);
        removeGraph();
    }


}