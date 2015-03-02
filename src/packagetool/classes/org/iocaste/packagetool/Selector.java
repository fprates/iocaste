package org.iocaste.packagetool;

import java.util.Map;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.protocol.IocasteException;

public class Selector {
    
    /**
     * 
     * @param taskname
     * @param group
     * @param state
     * @return
     */
    public static final ExtendedObject addEntry(String taskname,
            String groupname, int count, State state) {
        String entryid;
        ExtendedObject object;
        DocumentModel model = state.documents.getModel("TASK_ENTRY");
        
        entryid = String.format("%s%03d", groupname, count);
        
        object = new ExtendedObject(model);
        object.set("ID", entryid);
        object.set("NAME", taskname);
        object.set("GROUP", groupname);
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
        int counter;
        String msgid, name;
        DocumentModel model;
        ExtendedObject object;
        Map<String, String> messages;
        
        model = state.documents.getModel("TASK_ENTRY_TEXT");
        name = task.get("NAME");
        counter = 0;
        for (String locale : state.messages.keySet()) {
            messages = state.messages.get(locale);
            
            if (!messages.containsKey(name))
                continue;
            
            msgid = String.format("%s%03d", name, counter++);
            object = new ExtendedObject(model);
            object.set("ID", msgid);
            object.set("ENTRY", name);
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
    public static final void assignGroup(ExtendedObject group, String username,
            State state) throws Exception {
        DocumentModel model;
        ExtendedObject object;
        String groupid, groupname;
        
        groupname = group.get("NAME");
        groupid = new StringBuilder(groupname).
                append("_").append(username).toString();
        model = state.documents.getModel("USER_TASKS_GROUPS");
        
        object = new ExtendedObject(model);
        object.set("ID", groupid);
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
        
        object.set("NAME", name);
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
