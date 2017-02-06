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

import java.util.ArrayList;
import java.util.List;

import static greycat.Constants.END_OF_TIME;

public class ActionCheckForFuture implements Action {

    public void eval(final TaskContext ctx) {
        Long time = ctx.time();
        Object node;
        final boolean[] bool = {true};
        final List<Long> nodeIds = new ArrayList<Long>();
        TaskResultIterator it = ctx.resultAsNodes().iterator();
        while ((node = it.next()) != null) {
            final Node node1 = (Node) node;
            node1.timepoints(
                    time + 1, END_OF_TIME, new Callback<long[]>() {
                        public void on(long[] result) {
                            if (result.length != 0) {
                                bool[0] = false;
                                nodeIds.add(node1.id());
                            }
                        }
                    }
            );
        }
        if (bool[0]) {
            ctx.continueTask();
        } else {
            ctx.endTask(ctx.result(), new RuntimeException("Trying to modify the past of Node(s): " + nodeIds));
        }
    }

    public void serialize(StringBuilder builder) {
        builder.append(MLPActionNames.CHECK_FOR_FUTURE);
        builder.append(Constants.TASK_PARAM_OPEN);
        builder.append(Constants.TASK_PARAM_CLOSE);

    }

    @Override
    public String toString() {
        final StringBuilder res = new StringBuilder();
        serialize(res);
        return res.toString();
    }
}
