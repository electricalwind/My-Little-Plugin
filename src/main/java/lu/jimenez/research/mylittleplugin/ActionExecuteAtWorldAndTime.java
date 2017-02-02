/**
 * Copyright 2017 Matthieu Jimenez.  All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package lu.jimenez.research.mylittleplugin;

import org.mwg.Callback;
import org.mwg.Constants;
import org.mwg.internal.task.CF_Action;
import org.mwg.internal.task.CoreTask;
import org.mwg.internal.task.TaskHelper;
import org.mwg.plugin.SchedulerAffinity;
import org.mwg.task.ActionFunction;
import org.mwg.task.Task;
import org.mwg.task.TaskContext;
import org.mwg.task.TaskResult;

import java.util.Map;

import static org.mwg.task.Tasks.newTask;

public class ActionExecuteAtWorldAndTime extends CF_Action {

    private Task _task;
    private String _world;
    private String _time;

    public ActionExecuteAtWorldAndTime(final String p_world, final String p_time, final Task p_task) {
        this._task = p_task;
        this._time = p_time;
        this._world = p_world;
    }

    public Task[] children() {
        Task[] children_tasks = new Task[1];
        children_tasks[0] = _task;
        return children_tasks;
    }

    public void cf_serialize(StringBuilder builder, Map<Integer, Integer> dagIDS) {
        builder.append(MLPActionNames.EXECUTE_AT_WORLD_AND_TIME);

        builder.append(Constants.TASK_PARAM_OPEN);
        TaskHelper.serializeString(_world, builder, true);
        builder.append(Constants.TASK_PARAM_SEP);
        TaskHelper.serializeString(_time, builder, true);
        builder.append(Constants.TASK_PARAM_SEP);
        final CoreTask castedAction = (CoreTask) _task;
        final int castedActionHash = castedAction.hashCode();
        if (dagIDS == null || !dagIDS.containsKey(castedActionHash)) {
            castedAction.serialize(builder, dagIDS);
        } else {
            builder.append("" + dagIDS.get(castedActionHash));
        }
        builder.append(Constants.TASK_PARAM_CLOSE);
    }


    public void eval(final TaskContext ctx) {
        newTask()
                .thenDo(new ActionFunction() {
                    public void eval(TaskContext ctx) {
                        ctx.setVariable("time", ctx.time());
                        ctx.setVariable("world", ctx.world());
                        ctx.continueTask();
                    }
                })
                .travelInWorld(_world)
                .travelInTime(_time)
                .pipe(_task)
                .travelInWorld("{{world}}")
                .travelInTime("{{time}}")
                .executeFrom(ctx, ctx.result(), SchedulerAffinity.SAME_THREAD, new Callback<TaskResult>() {
                    public void on(TaskResult res) {
                        ctx.continueWith(res);

                    }
                });
    }
}
