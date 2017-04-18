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
package mylittleplugin.var;

import greycat.Action;
import greycat.Constants;
import greycat.TaskContext;
import greycat.TaskResult;
import greycat.internal.task.TaskHelper;
import greycat.struct.Buffer;
import mylittleplugin.MLPActionNames;

public class ActionFlipVarAndResult implements Action {
    private final String _var;


    public ActionFlipVarAndResult(final String p_var) {
        this._var = p_var;
    }

    public void eval(TaskContext ctx) {
        TaskResult value1 = ctx.variable(_var);
        TaskResult value2 = ctx.result();
        ctx.setVariable(_var, value2);
        ctx.continueWith(value1);
    }

    public void serialize(Buffer builder) {
        builder.writeString(MLPActionNames.FLIP_VAR_AND_RESULT);
        builder.writeChar(Constants.TASK_PARAM_OPEN);
        TaskHelper.serializeString(_var, builder, false);
        builder.writeChar(Constants.TASK_PARAM_CLOSE);
    }

    @Override
    public String name() {
        return MLPActionNames.FLIP_VAR_AND_RESULT;
    }

}
