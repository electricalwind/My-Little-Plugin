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
package mylittleplugin.traverse;


import greycat.*;
import greycat.base.BaseNode;
import greycat.internal.task.TaskHelper;
import greycat.plugin.Job;
import greycat.struct.Buffer;
import greycat.struct.Relation;
import greycat.struct.RelationIndexed;

import java.util.HashSet;
import java.util.Set;

import static mylittleplugin.MLPActionNames.TRAVERSE_DEDUP;

public class ActionTraverseDedup implements Action {

    private final String _name;
    private final String[] _params;


    public ActionTraverseDedup(final String p_name, final String... p_params) {
        this._name = p_name;
        this._params = p_params;
    }


    public final void eval(final TaskContext taskContext) {
        final TaskResult finalResult = taskContext.newResult();
        final String flatName = taskContext.template(_name);
        final TaskResult previousResult = taskContext.result();

        if (previousResult != null) {
            final int previousSize = previousResult.size();
            final DeferCounter defer = taskContext.graph().newCounter(previousSize);
            final HashSet<Long> idsFound = new HashSet<Long>();
            for (int i = 0; i < previousSize; i++) {
                final Object loop = previousResult.get(i);
                if (loop instanceof BaseNode) {
                    final Node casted = (Node) loop;

                    boolean relInde = false;
                    long[] relation;
                    int relationSize;
                    Set<Long> list = new HashSet<>();

                    switch (casted.type(flatName)) {
                        case Type.RELATION_INDEXED:

                            RelationIndexed relationIndexed = (RelationIndexed) casted.get(flatName);
                            if (relationIndexed != null) {
                                relation = relationIndexed.all();
                                relationSize = relationIndexed.size();

                                for (int j = 0; j < relationSize; j++) {
                                    if (idsFound.add(relation[j])) {
                                        list.add(relation[j]);
                                    }
                                }
                                if (_params != null && _params.length > 0) {
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
                                                    Node node = result[j];
                                                    if (node != null) {
                                                        if (list.contains(node.id())) {
                                                            finalResult.add(result[j]);
                                                        } else {
                                                            node.free();
                                                        }
                                                    }
                                                }
                                            }
                                            casted.free();
                                            defer.count();

                                        }
                                    });
                                    break;
                                } else {
                                    relInde = true;
                                }

                            } else {
                                casted.free();
                                defer.count();
                                break;
                            }
                        case Type.RELATION:
                            if (_params == null || _params.length == 0) {
                                if (!relInde) {
                                    Relation rel = (Relation) casted.get(flatName);
                                    relation = rel.all();
                                    relationSize = rel.size();
                                    for (int j = 0; j < relationSize; j++) {
                                        if (idsFound.add(relation[j])) {
                                            list.add(relation[j]);
                                        }
                                    }
                                }
                                casted.graph().lookupAll(casted.world(), casted.time(), list.stream().mapToLong(l -> l).toArray(), new Callback<Node[]>() {
                                    public void on(Node[] result) {
                                        if (result != null) {
                                            for (int j = 0; j < result.length; j++) {
                                                finalResult.add(result[j]);
                                            }
                                        }
                                        casted.free();
                                        defer.count();
                                    }
                                });
                            } else {
                                casted.free();
                                defer.count();
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
                    previousResult.clear();
                    taskContext.continueWith(finalResult);
                }
            });
        } else {
            taskContext.continueTask();
        }
    }

    @Override
    public void serialize(Buffer builder) {

        builder.writeString(TRAVERSE_DEDUP);
        builder.writeChar(Constants.TASK_PARAM_OPEN);
        TaskHelper.serializeString(_name,builder,true);
        if (_params != null && _params.length > 0) {
            builder.writeChar(Constants.TASK_PARAM_SEP);
            TaskHelper.serializeStringParams(_params, builder);
        }
        builder.writeChar(Constants.TASK_PARAM_CLOSE);
    }


}