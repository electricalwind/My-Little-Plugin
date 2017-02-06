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

import greycat.*;
import org.junit.jupiter.api.Test;

import static greycat.Tasks.newTask;
import static mylittleplugin.MyLittleActions.executeAtWorldAndTime;
import static org.junit.jupiter.api.Assertions.assertEquals;


class ActionExecuteAtWorldAndTimeTest extends ActionTest {

    @Test
    public void test() {
        initGraph();
        final int[] counter = {0};
        newTask().travelInTime("10")
                .then(executeAtWorldAndTime("0", "1",
                        newTask()
                                .createNode()
                                .setAttribute("name", Type.STRING, "newNode")
                                .defineAsVar("newNode")
                                .readGlobalIndex("roots")
                                .addVarToRelation("children", "newNode")
                        )
                )
                .thenDo(new ActionFunction() {
                    public void eval(TaskContext ctx) {
                        assertEquals(10, ctx.time());
                        ctx.resultAsNodes().get(0).relation("children", new Callback<Node[]>() {
                            public void on(Node[] result) {
                                assertEquals(3, result.length);
                            }
                        });
                        counter[0]++;
                        ctx.continueTask();
                    }
                })
                .travelInTime("0")
                .readGlobalIndex("roots")
                .traverse("children")
                .thenDo(new ActionFunction() {
                    public void eval(TaskContext ctx) {
                        assertEquals(2, ctx.result().size());
                        counter[0]++;
                        ctx.continueTask();
                    }
                })
                .travelInTime("2")
                .readGlobalIndex("roots")
                .traverse("children")
                .thenDo(new ActionFunction() {
                    public void eval(TaskContext ctx) {
                        assertEquals(3, ctx.result().size());
                        counter[0]++;
                        ctx.continueTask();
                    }
                }).execute(graph, null);

        assertEquals(3, counter[0]);
        removeGraph();
    }

}