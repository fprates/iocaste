package org.iocaste.tasksel;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.Message;

public class Refresh extends AbstractHandler {
    public Context extcontext;
    
    /**
     * 
     * @param context
     * @return
     */
    public static final Map<String, Set<TaskEntry>> getLists(
            PageBuilderContext context) {
        Query query;
        Set<TaskEntry> entries;
        TaskEntry entry;
        ExtendedObject[] result, mobject;
        Map<String, Set<TaskEntry>> lists;
        String groupname, language, taskname, username;
        Documents documents = new Documents(context.function);
        
        username = new Iocaste(context.function).getUsername();
        query = new Query();
        query.addColumns(
                "TASK_ENTRY.GROUP", "TASK_ENTRY.NAME", "TASK_ENTRY.ID");
        query.setModel("USER_TASKS_GROUPS");
        query.join("TASK_ENTRY", "USER_TASKS_GROUPS.GROUP", "GROUP");
        query.andEqual("USERNAME", username);
        result = documents.select(query);
        if (result == null)
            return null;
        
        language = context.view.getLocale().toString(); 
        lists = new LinkedHashMap<>();
        for (ExtendedObject object : result) {
            groupname = object.get("GROUP");
            if (lists.containsKey(groupname)) {
                entries = lists.get(groupname);
            } else {
                entries = new LinkedHashSet<>();
                lists.put(groupname, entries);
            }
            
            taskname = object.get("NAME");
            entry = new TaskEntry();
            entry.setName(taskname);
            entries.add(entry);
            
            query = new Query();
            query.setModel("TASK_ENTRY_TEXT");
            query.andEqual("ENTRY", taskname);
            query.andEqual("LANGUAGE", language);
            query.setMaxResults(1);
            mobject = documents.select(query);
            if (mobject != null)
                entry.setText((String)mobject[0].get("TEXT"));
        }
        
        return lists;
    }
    
    @Override
    public Object run(Message message) throws Exception {
        extcontext.groups = getLists(extcontext.context);
        extcontext.context.getView(Main.MAIN).getSpec().setInitialized(false);
        extcontext.context.function.setReloadableView(true);
        return null;
    }

}
