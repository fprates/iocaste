package org.iocaste.packagetool;

import java.math.BigDecimal;
import java.util.Map;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.protocol.IocasteException;

public class TaskSelector {
    
    /**
     * 
     * @param taskname
     * @param group
     * @param state
     * @return
     */
    public static final ExtendedObject addEntry(String taskname,
            ExtendedObject group, State state) {
        ExtendedObject object;
        int entryid = group.geti("CURRENT");
        int groupid = group.geti("ID");
        DocumentModel model = state.documents.getModel("TASK_ENTRY");
        
        if (entryid == 0)
            entryid = groupid * 1000;
        
        entryid++;
        group.set("CURRENT", entryid);
        state.documents.modify(group);
        
        object = new ExtendedObject(model);
        object.set("ID", entryid);
        object.set("NAME", taskname);
        object.set("GROUP", group.get("NAME"));
        state.documents.save(object);
        return object;
    }
    
    /**
     * 
     * @param task
     * @param group
     * @param state
     */
    public static final void addEntryMessage(ExtendedObject task,
            ExtendedObject group, State state) {
        String name;
        DocumentModel model;
        ExtendedObject object;
        Map<String, String> messages;
        int taskid = task.geti("ID");
        int msgid = taskid * 100;
        
        model = state.documents.getModel("TASK_ENTRY_TEXT");
        name = task.get("NAME");
        
        for (String locale : state.messages.keySet()) {
            messages = state.messages.get(locale);
            
            if (!messages.containsKey(name))
                continue;
            
            msgid++;
            object = new ExtendedObject(model);
            object.set("ID", msgid);
            object.set("TASK", taskid);
            object.set("LANGUAGE", locale);
            object.set("TEXT", messages.get(name));
            state.documents.save(object);
        }
    }
    
    /**
     * 
     * @param group
     * @param username
     * @param state
     * @throws Exception
     */
    public static final void assignGroup(ExtendedObject group,
            String username, State state) throws Exception {
        long taskid;
        DocumentModel model;
        ExtendedObject object;
        int userid, groupid = 0;
        String groupname = group.get("NAME");
        
        object = state.documents.getObject("LOGIN", username);
        userid = object.geti("ID");
        groupid = group.geti("ID");
        taskid = (userid * 1000) + groupid;
        model = state.documents.getModel("USER_TASKS_GROUPS");
        
        object = new ExtendedObject(model);
        object.set("ID", taskid);
        object.set("USERNAME", username);
        object.set("GROUP", groupname);
        if (state.documents.save(object) == 0)
            throw new IocasteException("error on assign group");
    }
    
    /**
     * 
     * @param name
     * @param state
     * @return
     */
    public static final ExtendedObject createGroup(String name, State state) {
        DocumentModel model = state.documents.getModel("TASKS_GROUPS");
        ExtendedObject object = new ExtendedObject(model);
        int id = new BigDecimal(state.documents.getNextNumber("TSKGROUP")).
                intValue();
        
        object.set("NAME", name);
        object.set("ID", id);
        object.set("CURRENT", 0);
        state.documents.save(object);
        
        return object;
    }
    
    /**
     * 
     * @param name
     * @param state
     * @return
     */
    public static final ExtendedObject getGroup(String name, State state) {
        return state.documents.getObject("TASKS_GROUPS", name);
    }
    
    /**
     * 
     * @param groupname
     * @param documents
     */
    public static final void removeGroup(String groupname, Documents documents)
    {
        Query[] queries = new Query[2];
        
        queries[0] = new Query("delete");
        queries[0].setModel("USER_TASKS_GROUPS");
        queries[0].andEqual("GROUP", groupname);
        
        queries[1] = new Query("delete");
        queries[1].setModel("TASKS_GROUPS");
        queries[1].andEqual("NAME", groupname);
        documents.update(queries);
    }
    
    /**
     * 
     * @param taskname
     * @param documents
     */
    public static final void removeTask(String taskname, Documents documents) {
        Query[] queries;
        int taskid;
        ExtendedObject[] task;
        Query query = new Query();
        
        query.setModel("TASK_ENTRY");
        query.andEqual("NAME", taskname);
        query.setMaxResults(1);
        task = documents.select(query);
        if (task == null)
            return;
        
        taskid = task[0].geti("ID");
        queries = new Query[2];
        queries[0] = new Query("delete");
        queries[0].setModel("TASK_ENTRY_TEXT");
        queries[0].andEqual("TASK", taskid);
        
        queries[1] = new Query("delete");
        queries[1].setModel("TASK_ENTRY");
        queries[1].andEqual("ID", taskid);
        documents.update(queries);
    }
}
