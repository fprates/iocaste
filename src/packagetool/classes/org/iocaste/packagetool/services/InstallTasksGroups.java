package org.iocaste.packagetool.services;

import java.util.HashMap;
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
        int entryid;
        Query query;
        String groupname, locale;
        Set<String> itens;
        ExtendedObject group;
        ExtendedObject[] tasks, entries, texts;
        Map<String, Map<String, String>> messages;
        Map<String, String> properties;
        
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

            /*
             * reindexa itens do grupo
             */
            query = new Query();
            query.setModel("TASK_ENTRY");
            query.andEqual("GROUP", groupname);
            entries = state.documents.select(query);
            
            if (entries != null) {
                query = new Query();
                query.setModel("TASK_ENTRY_TEXT");
                query.andEqual("GROUP", groupname);
                texts = state.documents.select(query);
                
                query = new Query("delete");
                query.setModel("TASK_ENTRY_TEXT");
                query.andEqual("GROUP", groupname);
                state.documents.update(query);
                
                query = new Query("delete");
                query.setModel("TASK_ENTRY");
                query.andEqual("GROUP", groupname);
                state.documents.update(query);

                for (ExtendedObject entry : entries)
                    itens.add(entry.getst("NAME"));

                messages = state.data.getMessages();
                if ((texts != null) && (messages != null))
                    for (ExtendedObject text : texts) {
                        locale = text.getst("LANGUAGE");
                        properties = messages.get(locale);
                        if (properties == null) {
                            properties = new HashMap<>();
                            messages.put(locale, properties);
                        }
                        properties.put(text.getst("ENTRY"), text.getst("TEXT"));
                    }
            }

            /*
             * instala entradas e textos
             */
            entryid = 0;
            for (String taskname : itens) {
                if (!containsTask(taskname, tasks))
                    throw new IocasteException(
                            new StringBuilder("invalid task \"").
                            append(taskname).append("\".").toString());
                
                Selector.add(taskname, groupname, entryid++, state);
                Registry.add(taskname, "TSKITEM", state);
            }
        }
    }

}