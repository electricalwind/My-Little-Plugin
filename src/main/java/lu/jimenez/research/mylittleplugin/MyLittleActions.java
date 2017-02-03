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

import org.mwg.task.Action;
import org.mwg.task.Task;

public class MyLittleActions {


    public static Action count() {
        return new ActionCount();
    }

    public static Action checkForFuture() {
        return new ActionCheckForFuture();
    }

    public static Action traverseOrAttributeInVar(final String p_name, final String p_variable, final String... p_params) {
        return new ActionTraverseOrAttributeInVar(p_name, p_variable, p_params);
    }

    public static Action ifEmptyThen(final Task then) {
        return new ActionIfEmptyThen(then);
    }

    public static Action ifNotEmptyThen(final Task then) {
        return new ActionIfNotEmptyThen(then);
    }

    public static Action ifEmptyThenElse(final Task then, final Task _else) {
        return new ActionIfEmptyThenElse(then, _else);
    }

    public static Action ifNotEmptyThenElse(final Task then, final Task _else) {
        return new ActionIfNotEmptyThenElse(then, _else);
    }

    public static Action injectAsVar(final String p_variable, final Object obj) {
        return new ActionInjectAsVar(p_variable, obj);
    }

    public static Action increment(final String p_variable, final int p_incrementValue) {
        return new ActionIncrement(p_variable, p_incrementValue);
    }

    public static Action keepFirstResult() {
        return new ActionKeepFirstResult();
    }

    public static Action flipVars(final String var1, final String var2) {
        return new ActionFlipVars(var1, var2);
    }

    public static Action flipVarAndResult(final String var) {
        return new ActionFlipVarAndResult(var);
    }

    public static Action executeAtWorldAndTime(final String world, final String time, final Task then) {
        return new ActionExecuteAtWorldAndTime(world, time, then);
    }

    public static Action readUpdatedTimeVar(final String name) {
        return new ActionReadUpdatedTimeVar(name);
    }

}
