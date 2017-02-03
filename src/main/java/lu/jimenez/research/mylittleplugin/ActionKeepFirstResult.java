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

import org.mwg.Constants;
import org.mwg.task.Action;
import org.mwg.task.TaskContext;

public class ActionKeepFirstResult implements Action {
    ActionKeepFirstResult() {
        super();
    }

    public void eval(final TaskContext taskContext) {
        if (taskContext.result().size() > 0)
            taskContext.continueWith(taskContext.wrap(taskContext.result().get(0)));
        else taskContext.continueTask();
    }

    public void serialize(StringBuilder builder) {
        builder.append(MLPActionNames.KEEP_FIRST_RESULT);
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
