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
import greycat.internal.task.TaskHelper;
import greycat.struct.Buffer;
import mylittleplugin.MLPActionNames;

public class ActionFlipVars implements Action {

    private final String _var1;
    private final String _var2;

    public ActionFlipVars(final String p_var1, final String p_var2) {
        this._var1 = p_var1;
        this._var2 = p_var2;
    }

    public void eval(TaskContext ctx) {
        Object value1 = ctx.variable(_var1);
        Object value2 = ctx.variable(_var2);
        ctx.setVariable(_var1, value2);
        ctx.setVariable(_var2, value1);
        ctx.continueTask();
    }

    public void serialize(Buffer builder) {
        builder.writeString(MLPActionNames.FLIP_VARS);
        builder.writeChar(Constants.TASK_PARAM_OPEN);
        TaskHelper.serializeString(_var1, builder, false);
        builder.writeChar(Constants.TASK_PARAM_SEP);
        TaskHelper.serializeString(_var2, builder, false);
        builder.writeChar(Constants.TASK_PARAM_CLOSE);
    }

    @Override
    public String name() {
        return MLPActionNames.FLIP_VARS;
    }

}
