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
package mylittleplugin.worldtime;


import greycat.*;
import greycat.base.BaseNode;
import greycat.internal.CoreDeferCounter;
import greycat.internal.task.TaskHelper;
import greycat.struct.Buffer;
import mylittleplugin.MLPActionNames;

public class ActionReadUpdatedTimeVar implements Action {

    private final String _name;
    private final String _origin;
    private final int _index;

    public ActionReadUpdatedTimeVar(String p_name) {
        this._origin = p_name;
        int indexEnd = -1;
        int indexStart = -1;
        int cursor = p_name.length() - 1;
        while (cursor > 0) {
            char c = p_name.charAt(cursor);
            if (c == ']') {
                indexEnd = cursor;
            } else if (c == '[') {
                indexStart = cursor + 1;
            }
            cursor--;
        }
        if (indexEnd != -1 && indexStart != -1) {
            _index = TaskHelper.parseInt(p_name.substring(indexStart, indexEnd));
            _name = p_name.substring(0, indexStart - 1);
        } else {
            _index = -1;
            _name = p_name;
        }
    }

    public void eval(final TaskContext ctx) {
        final String evaluatedName = ctx.template(_name);
        TaskResult varResult;
        if (_index != -1) {
            varResult = ctx.wrap(ctx.variable(evaluatedName).get(_index));
        } else {
            varResult = ctx.variable(evaluatedName);
        }
        if (varResult != null) {
            varResult = varResult.clone();
        }
        final TaskResult previous = varResult;
        final long time = ctx.time();
        if (previous == null) {
            ctx.endTask(ctx.result(), new RuntimeException("Unable to read var"));
        } else {
            final int previousSize = previous.size();
            final DeferCounter defer = new CoreDeferCounter(previousSize);
            for (int i = 0; i < previousSize; i++) {
                Object loopObj = previous.get(i);
                if (loopObj instanceof BaseNode) {
                    final Node castedPreviousNode = (Node) loopObj;
                    final int finalIndex = i;
                    castedPreviousNode.travelInTime(time, result -> {
                        castedPreviousNode.free();
                        previous.set(finalIndex, result);
                        defer.count();
                    });
                } else {
                    defer.count();
                }
            }
            defer.then(() -> ctx.continueWith(previous));
        }
    }


    public void serialize(Buffer builder) {
        builder.writeString(MLPActionNames.READ_UPDATED_TIME_VAR);
        builder.writeChar(Constants.TASK_PARAM_OPEN);
        TaskHelper.serializeString(_origin, builder, true);
        builder.writeChar(Constants.TASK_PARAM_CLOSE);
    }

    @Override
    public String name() {
        return MLPActionNames.READ_UPDATED_TIME_VAR;
    }

}
