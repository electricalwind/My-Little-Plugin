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

import org.mwg.task.Action;
import org.mwg.task.TaskContext;


public class ActionInjectAsVar implements Action {

    private final String _variable;
    private final Object _toInject;

    ActionInjectAsVar(final String p_variable, final Object toInject){
        _variable = p_variable;
        _toInject = toInject;
    }

    public void eval(final TaskContext taskContext) {
        taskContext.defineVariable(_variable,_toInject);
        taskContext.continueTask();
    }

    public void serialize(StringBuilder builder) {
        throw new RuntimeException("inject as var remote action not managed yet!");
    }

    @Override
    public String toString(){
        return "inject("+_toInject.toString()+","+_variable+")";
    }
}
