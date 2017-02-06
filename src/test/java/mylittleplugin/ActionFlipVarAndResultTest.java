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
package mylittleplugin;

import greycat.ActionFunction;
import greycat.TaskContext;
import greycat.utility.VerboseHook;
import org.junit.jupiter.api.Test;

import static greycat.Tasks.newTask;
import static mylittleplugin.MyLittleActions.flipVarAndResult;
import static org.junit.jupiter.api.Assertions.assertEquals;


class ActionFlipVarAndResultTest extends ActionTest {


    @Test
    public void testRes() {
        initGraph();
        final int[] counter = {0};
        newTask()
                .inject(1)
                .defineAsVar("var1")
                .inject(2)
                .then(flipVarAndResult("var1"))
                .thenDo(new ActionFunction() {
                    public void eval(TaskContext context) {
                        assertEquals(context.variable("var1").get(0), 2);
                        assertEquals(context.result().get(0), 1);
                        counter[0]++;
                        context.continueTask();
                    }
                }).addHook(new VerboseHook())
                .execute(graph, null);
        assertEquals(1, counter[0]);
        removeGraph();
    }
}