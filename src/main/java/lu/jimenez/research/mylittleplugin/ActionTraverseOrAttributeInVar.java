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

import org.mwg.*;
import org.mwg.base.BaseNode;
import org.mwg.internal.task.TaskHelper;
import org.mwg.plugin.Job;
import org.mwg.struct.RelationIndexed;
import org.mwg.task.Action;
import org.mwg.task.TaskContext;
import org.mwg.task.TaskResult;

public class ActionTraverseOrAttributeInVar implements Action {

    private final String _name;
    private final String _variable;
    private final String[] _params;

    ActionTraverseOrAttributeInVar(final String p_name, final String p_variable, final String... p_params) {
        super();
        this._name = p_name;
        this._variable = p_variable;
        this._params = p_params;
    }

    public void eval(final TaskContext taskContext) {
        final TaskResult finalResult = taskContext.newResult();
        final String flatName = taskContext.template(_name);
        final TaskResult previousResult = taskContext.result();
        if (previousResult != null) {
            final int previousSize = previousResult.size();
            final DeferCounter defer = taskContext.graph().newCounter(previousSize);
            for (int i = 0; i < previousSize; i++) {
                final Object loop = previousResult.get(i);
                if (loop instanceof BaseNode) {
                    final Node casted = (Node) loop;

                    switch (casted.type(flatName)) {
                        case Type.RELATION_INDEXED:
                            if (_params != null && _params.length > 0) {
                                RelationIndexed relationIndexed = (RelationIndexed) casted.get(flatName);
                                if (relationIndexed != null) {
                                    Query query = taskContext.graph().newQuery();
                                    query.setWorld(taskContext.world());
                                    query.setTime(taskContext.time());
                                    String previous = null;
                                    for (int k = 0; k < _params.length; k++) {
                                        if (previous != null) {
                                            query.add(previous, _params[k]);
                                            previous = null;
                                        } else {
                                            previous = _params[k];
                                        }
                                    }
                                    relationIndexed.findByQuery(query, new Callback<Node[]>() {

                                        public void on(Node[] result) {
                                            if (result != null) {
                                                for (int j = 0; j < result.length; j++) {
                                                    if (result[j] != null) {
                                                        finalResult.add(result[j]);
                                                    }
                                                }
                                            }
                                            defer.count();
                                        }
                                    });
                                } else {
                                    defer.count();
                                }
                            }
                        case Type.RELATION:
                            if (_params == null || _params.length == 0) {
                                casted.relation(flatName, new Callback<Node[]>() {

                                    public void on(Node[] result) {
                                        if (result != null) {
                                            for (int j = 0; j < result.length; j++) {
                                                finalResult.add(result[j]);
                                            }
                                        }
                                        defer.count();
                                    }
                                });
                            }
                            break;
                        default:
                            Object resolved = casted.get(flatName);
                            if (resolved != null) {
                                finalResult.add(resolved);
                            }
                            defer.count();
                            break;

                    }
                } else {
                    //TODO add closable management
                    finalResult.add(loop);
                    defer.count();
                }
            }
            defer.then(new Job() {
                public void run() {
                    taskContext.defineVariable(taskContext.template(_variable), finalResult);
                    taskContext.continueTask();
                }
            });
        } else {
            taskContext.continueTask();
        }
    }

    public void serialize(StringBuilder builder) {
        builder.append(MLPActionNames.TRAVERSE_OR_ATTRIBUTE_IN_VAR);
        builder.append(Constants.TASK_PARAM_OPEN);
        TaskHelper.serializeString(_name, builder, false);
        builder.append(Constants.TASK_PARAM_SEP);
        TaskHelper.serializeString(_variable, builder, false);
        builder.append(Constants.TASK_PARAM_SEP);
        if (_params != null && _params.length > 0) {
            builder.append(Constants.TASK_PARAM_SEP);
            TaskHelper.serializeStringParams(_params, builder);
        }
        builder.append(Constants.TASK_PARAM_CLOSE);


    }

    @Override
    public String toString() {
        final StringBuilder res = new StringBuilder();
        serialize(res);
        return res.toString();
    }
}
