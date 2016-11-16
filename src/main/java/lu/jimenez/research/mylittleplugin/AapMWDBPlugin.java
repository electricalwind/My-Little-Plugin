package lu.jimenez.research.mylittleplugin;

import lu.jimenez.research.mylittleplugin.actions.ActionCount;
import lu.jimenez.research.mylittleplugin.actions.ActionGetAsVar;
import org.mwg.plugin.AbstractPlugin;
import org.mwg.task.TaskAction;
import org.mwg.task.TaskActionFactory;

public class AapMWDBPlugin extends AbstractPlugin {

    public AapMWDBPlugin(){

        declareTaskAction(AapMWDBActions.COUNT, new TaskActionFactory() {
            public TaskAction create(String[] strings) {
                return new ActionCount();
            }
        });

        declareTaskAction(AapMWDBActions.GET_AS_VAR, new TaskActionFactory() {
            public TaskAction create(String[] strings) {
                if (strings.length != 2) {
                    throw new RuntimeException(AapMWDBActions.GET_AS_VAR + " action need 2 parameter");
                }
                return new ActionGetAsVar(strings[0],strings[1]);
            }
        });
    }
}
