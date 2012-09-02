package org.iocaste.packagetool;

import java.util.Map;
import java.util.Set;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.packagetool.common.TaskGroup;
import org.iocaste.protocol.user.User;

public class InstallTasksGroups {
    
    /**
     * 
     * @param tasksgroups
     * @param state
     * @throws Exception
     */
    public static final void init(Map<TaskGroup, Set<User>> tasksgroups,
            State state) throws Exception {
        String groupname;
        Set<String> itens;
        ExtendedObject task, group;
        
        for (TaskGroup taskgroup : tasksgroups.keySet()) {
            groupname = taskgroup.getName();
            group = TaskSelector.getGroup(groupname, state);
            if (group == null) {
                group = TaskSelector.createGroup(groupname, state);
                TaskSelector.assignGroup(group, "ADMIN", state);
                for (User user : tasksgroups.get(taskgroup))
                    TaskSelector.assignGroup(group, user.getUsername(), state);
                Registry.add(groupname, "TSKGROUP", state);
            }

            itens = taskgroup.getLinks();
            for (String taskname : itens) {
                task = TaskSelector.addEntry(taskname, group, state);
                if (state.messages.size() > 0)
                    TaskSelector.addEntryMessage(task, group, state);
                
                Registry.add(taskname, "TSKITEM", state);
            }
        }
    }

}
