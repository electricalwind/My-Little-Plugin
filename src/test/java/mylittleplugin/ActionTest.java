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
package mylittleplugin;


import greycat.*;
import greycat.scheduler.NoopScheduler;

import static greycat.Constants.BEGINNING_OF_TIME;
import static org.junit.jupiter.api.Assertions.assertEquals;


public abstract class ActionTest {

    protected Graph graph;
    protected long startMemory;

    protected void initGraph() {
        graph = new GraphBuilder().withScheduler(new NoopScheduler()).build();
        final ActionTest selfPointer = this;
        graph.connect(new Callback<Boolean>() {

            public void on(Boolean result) {

                //create graph nodes
                final Node n0 = selfPointer.graph.newNode(0, BEGINNING_OF_TIME);
                n0.set("name", Type.STRING, "n0");
                n0.set("value", Type.INT, 8);

                final Node n1 = selfPointer.graph.newNode(0, BEGINNING_OF_TIME);
                n1.set("name", Type.STRING, "n1");
                n1.set("value", Type.INT, 3);

                final Node root = selfPointer.graph.newNode(0, BEGINNING_OF_TIME);
                root.set("name", Type.STRING, "root");
                root.addToRelation("children", n0);
                root.addToRelation("children", n1);

                //create some index
                selfPointer.graph.declareIndex(0, "roots", new Callback<NodeIndex>() {
                    @Override
                    public void on(NodeIndex rootsIndex) {
                        rootsIndex.update(root);
                        // rootsIndex.free();
                    }
                }, "name");
                selfPointer.graph.declareIndex(0, "nodes", new Callback<NodeIndex>() {
                    @Override
                    public void on(NodeIndex nodesIndex) {
                        nodesIndex.update(n0);
                        nodesIndex.update(n1);
                        nodesIndex.update(root);
                        // nodesIndex.free();
                    }
                }, "name");

            }
        });
    }

    protected void initGraphR() {
        graph = new GraphBuilder().withScheduler(new NoopScheduler()).build();
        final ActionTest selfPointer = this;
        graph.connect(new Callback<Boolean>() {

            public void on(Boolean result) {

                //create graph nodes
                final Node n0 = selfPointer.graph.newNode(0, BEGINNING_OF_TIME);
                n0.set("name", Type.STRING, "n0");
                n0.set("value", Type.INT, 8);

                final Node n1 = selfPointer.graph.newNode(0, BEGINNING_OF_TIME);
                n1.set("name", Type.STRING, "n1");
                n1.set("value", Type.INT, 3);

                final Node root = selfPointer.graph.newNode(0, BEGINNING_OF_TIME);
                root.set("name", Type.STRING, "root");
                Index index = (Index) root.getOrCreate("children", Type.INDEX);
                index.declareAttributes(new Callback() {
                    @Override
                    public void on(Object result) {

                    }
                }, "name");
                index.update(n0);
                index.update(n1);

                //create some index
                selfPointer.graph.declareIndex(0, "roots", new Callback<NodeIndex>() {
                    @Override
                    public void on(NodeIndex rootsIndex) {
                        rootsIndex.update(root);
                        // rootsIndex.free();
                    }
                }, "name");
                selfPointer.graph.declareIndex(0, "nodes", new Callback<NodeIndex>() {
                    @Override
                    public void on(NodeIndex nodesIndex) {
                        nodesIndex.update(n0);
                        nodesIndex.update(n1);
                        nodesIndex.update(root);
                        // nodesIndex.free();
                    }
                }, "name");
            }
        });
    }


    protected void removeGraph() {
        graph.disconnect(new Callback<Boolean>() {
            public void on(Boolean result) {
                assertEquals(true, result);
            }
        });
    }

    protected void startMemoryLeakTest() {
        graph.save(new Callback<Boolean>() {
            public void on(Boolean result) {
                startMemory = graph.space().available();
            }
        });
    }

    protected void endMemoryLeakTest() {
        graph.save(new Callback<Boolean>() {
            public void on(Boolean result) {
                assertEquals(startMemory, graph.space().available());
            }
        });
    }
}
