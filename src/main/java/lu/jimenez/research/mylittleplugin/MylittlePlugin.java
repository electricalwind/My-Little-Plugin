package lu.jimenez.research.mylittleplugin;

import lu.jimenez.research.mylittleplugin.actions.ActionCount;
import lu.jimenez.research.mylittleplugin.actions.ActionGetAsVar;
import lu.jimenez.research.mylittleplugin.actions.ActionGetOrCreate;
import lu.jimenez.research.mylittleplugin.actions.ActionIncrement;
import org.mwg.plugin.AbstractPlugin;
import org.mwg.task.TaskAction;
import org.mwg.task.TaskActionFactory;

public class MylittlePlugin extends AbstractPlugin {

    public MylittlePlugin() {

        declareTaskAction(MyLittleActions.COUNT, new TaskActionFactory() {
            public TaskAction create(String[] strings) {
                return new ActionCount();
            }
        });

        declareTaskAction(MyLittleActions.GET_AS_VAR, new TaskActionFactory() {
            public TaskAction create(String[] strings) {
                if (strings.length != 2) {
                    throw new RuntimeException(MyLittleActions.GET_AS_VAR + " action need 2 parameter");
                }
                return new ActionGetAsVar(strings[0], strings[1]);
            }
        });

        declareTaskAction(MyLittleActions.GET_OR_CREATE, new TaskActionFactory() {
            public TaskAction create(String[] strings) {
                if (strings.length != 2) {
                    throw new RuntimeException(MyLittleActions.GET_OR_CREATE + " action need 2 parameter");
                }
                return new ActionGetOrCreate(strings[0], strings[1]);
            }
        });

        declareTaskAction(MyLittleActions.INCREMENT, new TaskActionFactory() {
            public TaskAction create(String[] strings) {
                if (strings.length != 2) {
                    throw new RuntimeException(MyLittleActions.INCREMENT + " action need 2 parameter");
                }
                return new ActionIncrement(strings[0], strings[1]);
            }
        });
    }
}
