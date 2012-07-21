package org.iocaste.packagetool;

import java.util.Map;
import java.util.Set;

import org.iocaste.documents.common.ExtendedObject;

public class InstallTasksGroups {
    
    /**
     * 
     * @param tasksgroups
     * @param state
     * @throws Exception
     */
    public static final void init(Map<String, Set<String>> tasksgroups,
            State state) throws Exception {
        Set<String> itens;
        ExtendedObject task, group;
        
        for (String groupname : tasksgroups.keySet()) {
            group = TaskSelector.getGroup(groupname, state);
            if (group == null) {
                group = TaskSelector.createGroup(groupname, state);
                TaskSelector.assignGroup(group, "ADMIN", state);
                Registry.add(groupname, "TSKGROUP", state);
            }

            itens = tasksgroups.get(groupname);
            for (String taskname : itens) {
                task = TaskSelector.addEntry(taskname, group, state);
                if (state.messages.size() > 0)
                    TaskSelector.addEntryMessage(task, group, state);
                
                Registry.add(taskname, "TSKITEM", state);
            }
        }
    }

}
