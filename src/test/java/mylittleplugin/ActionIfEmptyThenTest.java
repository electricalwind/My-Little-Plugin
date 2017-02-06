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
import org.junit.jupiter.api.Test;

import static greycat.Tasks.newTask;
import static greycat.internal.task.CoreActions.*;
import static mylittleplugin.MyLittleActions.ifEmptyThen;
import static org.junit.jupiter.api.Assertions.assertEquals;


class ActionIfEmptyThenTest extends ActionTest {

    @Test
    public void test() {
        initGraph();
        final int[] counter = {0};
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
                }).execute(graph, null);
        assertEquals(1, counter[0]);
        removeGraph();
    }


}