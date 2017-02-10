package mylittleplugin;

/**
 * Copyright 2017 The GreyCat Authors.  All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import greycat.*;
import greycat.base.BaseNode;
import greycat.internal.task.TaskHelper;
import greycat.plugin.Job;
import greycat.struct.Relation;
import greycat.struct.RelationIndexed;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static mylittleplugin.MLPActionNames.TRAVERSE_DEDUP;

class ActionTraverseDedup implements Action {

    private final String _name;
    private final String[] _params;


    ActionTraverseDedup(final String p_name, final String... p_params) {
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

                    switch (casted.type(flatName)) {
                        case Type.RELATION_INDEXED:
                            if (_params != null && _params.length > 0) {
                                RelationIndexed relationIndexed = (RelationIndexed) casted.get(flatName);
                                long[] relation = relationIndexed.all();
                                List<Long> list = new ArrayList();
                                for (int j = 0; j < relation.length; j++) {
                                    if (idsFound.add(relation[j])) {
                                        list.add(relation[j]);
                                    }
                                }
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

                                            defer.count();
                                        }
                                    });
                                } else {
                                    defer.count();
                                }
                                casted.free();
                            }
                        case Type.RELATION:
                            if (_params == null || _params.length == 0) {
                                long[] relation = ((Relation) casted.get(flatName)).all();
                                List<Long> list = new ArrayList();
                                for (int j = 0; j < relation.length; j++) {
                                    if (idsFound.add(relation[j])) {
                                        list.add(relation[j]);
                                    }
                                }
                                casted.graph().lookupAll(casted.world(), casted.time(), list.stream().mapToLong(l -> l).toArray(), new Callback<Node[]>() {
                                    public void on(Node[] result) {
                                        if (result != null) {
                                            for (int j = 0; j < result.length; j++) {
                                                finalResult.add(result[j]);
                                            }
                                        }
                                    }
                                });
                            }
                            casted.free();
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
    public void serialize(StringBuilder builder) {

        builder.append(TRAVERSE_DEDUP);
        builder.append(Constants.TASK_PARAM_OPEN);
        builder.append(_name);
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