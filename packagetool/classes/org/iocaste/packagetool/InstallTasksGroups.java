package org.iocaste.packagetool;

import java.util.Map;
import java.util.Set;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.packagetool.common.TaskGroup;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.user.User;

public class InstallTasksGroups {
    
    private static final boolean containsTask(String name,
            ExtendedObject[] tasks) {
        for (ExtendedObject task : tasks)
            if (task.getValue("NAME").equals(name))
                return true;
        
        return false;
    }
    
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
        ExtendedObject[] tasks;
        
        tasks = state.documents.select("from TASKS");
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
                if (!containsTask(taskname, tasks))
                    throw new IocasteException(
                            new StringBuilder("invalid task \"").
                            append(taskname).append("\".").toString());
                
                task = TaskSelector.addEntry(taskname, group, state);
                if (state.messages.size() > 0)
                    TaskSelector.addEntryMessage(task, group, state);
                
                Registry.add(taskname, "TSKITEM", state);
            }
        }
    }

}
