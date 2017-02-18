package mylittleplugin.traverse.lazy;

import greycat.*;
import greycat.base.BaseNode;
import greycat.plugin.Job;
import greycat.struct.Buffer;
import greycat.struct.Relation;
import greycat.struct.RelationIndexed;

import java.util.ArrayList;
import java.util.List;

public class ActionLazyTraverse implements Action {

    private final String _name;
    private final String[] _params;

    public ActionLazyTraverse(final String p_name, final String... p_params) {
        this._name = p_name;
        this._params = p_params;
    }

    @Override
    public void eval(TaskContext ctx) {
        final TaskResult finalResult = ctx.newResult();
        final String flatName = ctx.template(_name);
        final TaskResult previousResult = ctx.result();
        final List<Long> toLoad = new ArrayList<>();

        if (previousResult != null) {
            final int previousSize = previousResult.size();
            final DeferCounter defer = ctx.graph().newCounter(previousSize);
            for (int i = 0; i < previousSize; i++) {
                final Object loop = previousResult.get(i);
                if (loop instanceof BaseNode) {
                    final Node casted = (Node) loop;
                    Object resolved = casted.get(flatName);
                    if (resolved != null) {
                        if (resolved instanceof RelationIndexed && _params.length > 0) {
                            RelationIndexed relationIndexed = (RelationIndexed) resolved;
                            Query query = ctx.graph().newQuery();
                            query.setWorld(ctx.world());
                            query.setTime(ctx.time());
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
                                @Override
                                public void on(Node[] result) {
                                    if (result != null) {
                                        for (int j = 0; j < result.length; j++) {
                                            if (result[j] != null) {
                                                finalResult.add(result[j]);
                                            }
                                        }
                                    }
                                    casted.free();
                                    defer.count();
                                }
                            });
                        } else {
                            finalResult.add(resolved);
                            casted.free();
                            defer.count();
                        }
                    } else {
                        casted.free();
                        defer.count();
                    }
                } else {
                    if (loop instanceof Relation) {
                        final Relation rel = (Relation) loop;
                        long[] nodes = rel.all();
                        for (int k = 0; k < nodes.length; k++) {
                            toLoad.add(nodes[k]);
                        }
                        defer.count();
                    } else {
                        if (loop instanceof RelationIndexed) {
                            final RelationIndexed rel = (RelationIndexed) loop;
                            long[] nodes = rel.all();
                            for (int k = 0; k < nodes.length; k++) {
                                toLoad.add(nodes[k]);
                            }
                            defer.count();
                        }
                    }
                }

            }
            defer.then(new Job() {
                @Override
                public void run() {
                    final int size = toLoad.size();
                    if (size > 0) {
                        long[] nodes = new long[size];
                        for (int i = 0; i < size; i++) {
                            nodes[i] = toLoad.get(i);
                        }
                        ctx.graph().lookupAll(ctx.world(), ctx.time(), nodes, new Callback<Node[]>() {
                            @Override
                            public void on(Node[] result) {
                                if (result != null) {
                                    final DeferCounter defer2 = ctx.graph().newCounter(result.length);
                                    for (int i = 0; i < result.length; i++) {
                                        final Node node = result[i];
                                        Object resolved = node.get(flatName);
                                        if (resolved != null) {
                                            if (resolved instanceof RelationIndexed && _params.length > 0) {
                                                RelationIndexed relationIndexed = (RelationIndexed) resolved;
                                                Query query = ctx.graph().newQuery();
                                                query.setWorld(ctx.world());
                                                query.setTime(ctx.time());
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
                                                    @Override
                                                    public void on(Node[] result) {
                                                        if (result != null) {
                                                            for (int j = 0; j < result.length; j++) {
                                                                if (result[j] != null) {
                                                                    finalResult.add(result[j]);
                                                                }
                                                            }
                                                        }
                                                        node.free();
                                                        defer.count();
                                                    }
                                                });
                                            } else {
                                                finalResult.add(resolved);
                                                node.free();
                                                defer.count();
                                            }
                                        } else {
                                            node.free();
                                            defer.count();
                                        }
                                    }
                                    defer2.then(new Job() {
                                        @Override
                                        public void run() {
                                            previousResult.clear();
                                            ctx.continueWith(finalResult);
                                        }
                                    });
                                } else {
                                    previousResult.clear();
                                    ctx.continueWith(finalResult);
                                }
                            }
                        });
                    } else {
                        previousResult.clear();
                        ctx.continueWith(finalResult);
                    }
                }
            });
        }else {
            ctx.continueTask();
        }

    }

    @Override
    public void serialize(Buffer builder) {

    }
}
