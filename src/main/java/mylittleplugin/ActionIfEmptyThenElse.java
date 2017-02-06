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
import greycat.internal.task.CF_Action;
import greycat.internal.task.CoreTask;
import greycat.plugin.SchedulerAffinity;

import java.util.Map;

public class ActionIfEmptyThenElse extends CF_Action {

    private Task _actionThen;
    private Task _actionElse;

    ActionIfEmptyThenElse(final Task actionThen, final Task actionElse) {
        super();
        if (actionThen == null) {
            throw new RuntimeException("thenSub should not be null");
        }
        if (actionElse == null) {
            throw new RuntimeException("elseSub should not be null");
        }
        this._actionThen = actionThen;
        this._actionElse = actionElse;
    }

    public Task[] children() {
        Task[] children_tasks = new Task[2];
        children_tasks[0] = _actionThen;
        children_tasks[0] = _actionElse;
        return children_tasks;
    }

    public void cf_serialize(StringBuilder builder, Map<Integer, Integer> dagIDS) {
        builder.append(MLPActionNames.IF_EMPTY_THEN_ELSE);
        builder.append(Constants.TASK_PARAM_OPEN);
        final CoreTask castedActionT = (CoreTask) _actionThen;
        final int castedActionHashT = castedActionT.hashCode();
        if (dagIDS == null || !dagIDS.containsKey(castedActionHashT)) {
            castedActionT.serialize(builder, dagIDS);
        } else {
            builder.append("" + dagIDS.get(castedActionHashT));
        }
        builder.append(Constants.TASK_PARAM_SEP);
        final CoreTask castedActionE = (CoreTask) _actionElse;
        final int castedActionHashE = castedActionE.hashCode();
        if (dagIDS == null || !dagIDS.containsKey(castedActionHashE)) {
            castedActionE.serialize(builder, dagIDS);
        } else {
            builder.append("" + dagIDS.get(castedActionHashE));
        }
        builder.append(Constants.TASK_PARAM_CLOSE);
    }

    public void eval(final TaskContext taskContext) {
        if (taskContext.result().size() == 0) {
            _actionThen.executeFrom(taskContext, taskContext.result(), SchedulerAffinity.SAME_THREAD, new Callback<TaskResult>() {
                public void on(TaskResult res) {
                    taskContext.continueWith(res);
                }
            });
        } else
            _actionElse.executeFrom(taskContext, taskContext.result(), SchedulerAffinity.SAME_THREAD, new Callback<TaskResult>() {
                public void on(TaskResult res) {
                    taskContext.continueWith(res);
                }
            });
    }
}
