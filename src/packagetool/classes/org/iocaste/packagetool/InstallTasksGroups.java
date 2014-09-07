package org.iocaste.packagetool;

import java.util.Map;
import java.util.Set;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.packagetool.common.TaskGroup;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.user.User;

public class InstallTasksGroups {
    
    private static final boolean containsTask(String name,
            ExtendedObject[] tasks) {
        for (ExtendedObject task : tasks)
            if (task.get("NAME").equals(name))
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
        Query query;
        String groupname;
        Set<String> itens;
        ExtendedObject task, group;
        ExtendedObject[] tasks;
        
        query = new Query();
        query.setModel("TASKS");
        tasks = state.documents.select(query);
        for (TaskGroup taskgroup : tasksgroups.keySet()) {
            groupname = taskgroup.getName();
            group = Selector.getGroup(groupname, state);
            if (group == null) {
                group = Selector.createGroup(groupname, state);
                Selector.assignGroup(group, "ADMIN", state);
                for (User user : tasksgroups.get(taskgroup))
                    Selector.assignGroup(group, user.getUsername(), state);
                Registry.add(groupname, "TSKGROUP", state);
            }

            itens = taskgroup.getLinks();
            
            for (String taskname : itens) {
                if (!containsTask(taskname, tasks))
                    throw new IocasteException(
                            new StringBuilder("invalid task \"").
                            append(taskname).append("\".").toString());
                
                task = Selector.addEntry(taskname, group, state);
                if (state.messages.size() > 0)
                    Selector.addEntryMessage(task, group, state);
                
                Registry.add(taskname, "TSKITEM", state);
            }
        }
    }

}
