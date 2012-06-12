package org.iocaste.packagetool;

import java.math.BigDecimal;
import java.util.Map;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;

public class TaskSelector {
    private static final byte DEL_USR_TSK_GRP = 0;
    private static final byte DEL_TASK = 1;
    private static final byte DEL_TASK_TEXT = 2;
    private static final byte DEL_TSK_GRP = 3;
    private static final byte TASK = 4;
    private static final String[] QUERIES = {
        "delete from USER_TASKS_GROUPS where GROUP = ?",
        "delete from TASK_ENTRY where ID = ?",
        "delete from TASK_ENTRY_TEXT where TASK = ?",
        "delete from TASKS_GROUPS where NAME = ?",
        "from TASK_ENTRY where NAME = ?"
    };
    
    /**
     * 
     * @param taskname
     * @param group
     * @param state
     * @return
     * @throws Exception
     */
    public static final ExtendedObject addEntry(String taskname,
            ExtendedObject group, State state) throws Exception {
        ExtendedObject object;
        int entryid = group.getValue("CURRENT");
        int groupid = group.getValue("ID");
        DocumentModel model = state.documents.getModel("TASK_ENTRY");
        
        if (entryid == 0)
            entryid = groupid * 1000;
        
        entryid++;
        group.setValue("CURRENT", entryid);
        state.documents.modify(group);
        
        object = new ExtendedObject(model);
        object.setValue("ID", entryid);
        object.setValue("NAME", taskname);
        object.setValue("GROUP", group.getValue("NAME"));
        
        state.documents.save(object);
        
        return object;
    }
    
    /**
     * 
     * @param task
     * @param group
     * @param state
     * @throws Exception
     */
    public static final void addEntryMessage(ExtendedObject task,
            ExtendedObject group, State state) throws Exception {
        String name;
        DocumentModel model;
        ExtendedObject object;
        Map<String, String> messages;
        int taskid = task.getValue("ID");
        int msgid = taskid * 100;
        
        model = state.documents.getModel("TASK_ENTRY_TEXT");
        name = task.getValue("NAME");
        
        for (String locale : state.messages.keySet()) {
            messages = state.messages.get(locale);
            
            if (!messages.containsKey(name))
                continue;
            
            msgid++;
            object = new ExtendedObject(model);
            object.setValue("ID", msgid);
            object.setValue("TASK", taskid);
            object.setValue("LANGUAGE", locale);
            object.setValue("TEXT", messages.get(name));
            state.documents.save(object);
        }
    }
    
    /**
     * 
     * @param groupname
     * @param username
     * @param state
     * @return
     * @throws Exception
     */
    public static final void assignGroup(ExtendedObject group,
            String username, State state) throws Exception {
        long taskid;
        DocumentModel model;
        ExtendedObject object;
        int userid, groupid = 0;
        String groupname = group.getValue("NAME");
        
        object = state.documents.getObject("LOGIN", username);
        userid = object.getValue("ID");
        groupid = group.getValue("ID");
        taskid = (userid * 1000) + groupid;
        model = state.documents.getModel("USER_TASKS_GROUPS");
        
        object = new ExtendedObject(model);
        object.setValue("ID", taskid);
        object.setValue("USERNAME", username);
        object.setValue("GROUP", groupname);
        state.documents.save(object);
    }
    
    /**
     * 
     * @param name
     * @param state
     * @return
     * @throws Exception
     */
    public static final ExtendedObject createGroup(String name, State state)
            throws Exception {
        DocumentModel model = state.documents.getModel("TASKS_GROUPS");
        ExtendedObject object = new ExtendedObject(model);
        int id = new BigDecimal(state.documents.getNextNumber("TSKGROUP")).
                intValue();
        
        object.setValue("NAME", name);
        object.setValue("ID", id);
        object.setValue("CURRENT", 0);
        
        state.documents.save(object);
        
        return object;
    }
    
    /**
     * 
     * @param name
     * @param state
     * @return
     * @throws Exception
     */
    public static final ExtendedObject getGroup(String name, State state)
            throws Exception {
        return state.documents.getObject("TASKS_GROUPS", name);
    }
    
    /**
     * 
     * @param groupname
     * @param documents
     * @throws Exception
     */
    public static final void removeGroup(String groupname, Documents documents)
            throws Exception {
        documents.update(QUERIES[DEL_USR_TSK_GRP], groupname);
        documents.update(QUERIES[DEL_TSK_GRP], groupname);
    }
    
    /**
     * 
     * @param taskname
     * @param documents
     * @throws Exception
     */
    public static final void removeTask(String taskname, Documents documents)
            throws Exception {
        int taskid;
        ExtendedObject[] task;
        
        task = documents.selectLimitedTo(QUERIES[TASK], 1, taskname);
        if (task == null)
            return;
        
        taskid = task[0].getValue("ID");
        documents.update(QUERIES[DEL_TASK_TEXT], taskid);
        documents.update(QUERIES[DEL_TASK], taskid);
    }
}
