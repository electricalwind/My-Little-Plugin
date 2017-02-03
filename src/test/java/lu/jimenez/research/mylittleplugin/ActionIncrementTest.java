/**
 * Copyright 2017 Matthieu Jimenez.  All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package lu.jimenez.research.mylittleplugin;

import org.junit.jupiter.api.Test;
import org.mwg.task.ActionFunction;
import org.mwg.task.TaskContext;

import static lu.jimenez.research.mylittleplugin.MyLittleActions.increment;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mwg.internal.task.CoreActions.addToVar;
import static org.mwg.internal.task.CoreActions.inject;
import static org.mwg.task.Tasks.newTask;

class ActionIncrementTest extends ActionTest {

    @Test
    public void test() {
        initGraph();
        final int[] counter = {0};
        newTask()
                .then(inject(1))
                .then(addToVar("inc"))
                .then(increment("inc", 1))
                .thenDo(new ActionFunction() {
                    public void eval(TaskContext context) {
                        assertEquals(context.variable("inc").get(0), 2);
                        counter[0]++;
                        context.continueTask();
                    }
                })
                .then(increment("inc", 2))
                .thenDo(new ActionFunction() {
                    public void eval(TaskContext context) {
                        assertEquals(context.variable("inc").get(0), 4);
                        counter[0]++;
                        context.continueTask();
                    }
                })
                .execute(graph, null);
        assertEquals(2, counter[0]);
        removeGraph();
    }

}