package org.iocaste.tasksel.groups;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.protocol.Iocaste;
import org.iocaste.shell.common.Shell;
import org.iocaste.tasksel.Context;

public class GetLists extends AbstractActionHandler {

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        Query query;
        ExtendedObject entry;
        ExtendedObject[] result;
        String groupname, language, taskname, username, packagename;
        Map<String, String> _messages, groups;
        Map<String, Map<String, String>> messages;
        Iocaste iocaste = new Iocaste(context.function);
        Context extcontext = getExtendedContext();
        
        extcontext.entries = null;
        username = iocaste.getUsername();
        query = new Query();
        query.addColumns(
                "TASK_ENTRY.GROUP",
                "TASK_ENTRY.NAME",
                "TASK_ENTRY.ID",
                "TASK_ENTRY.PACKAGE"
        );
        query.setModel("USER_TASKS_GROUPS");
        query.join("TASK_ENTRY", "USER_TASKS_GROUPS.GROUP", "GROUP");
        query.andEqual("USERNAME", username);
        result = select(query);
        if (result == null)
            return;
        
        language = context.view.getLocale().toString();
        extcontext.entries = new LinkedHashSet<>();
        messages = new HashMap<>();
        groups = new HashMap<>();
        for (ExtendedObject object : result) {
            groupname = object.get("GROUP");
            taskname = object.getst("NAME");
            packagename = object.getst("PACKAGE");
            
            _messages = messages.get(packagename);
            if (_messages == null) {
                _messages = Shell.
                        getMessages(context.function, language, packagename);
                messages.put(packagename, _messages);
            }
            
            if ((_messages != null) && _messages.containsKey(groupname))
                groups.put(groupname, getText(_messages, groupname));
            
            entry = instance("TASK_TILE_ENTRY");
            entry.set("GROUP", groupname);
            entry.set("NAME", taskname);
            entry.set("TEXT", getText(_messages, taskname));
            extcontext.entries.add(entry);
        }
        
        for (ExtendedObject object : extcontext.entries) {
            groupname = groups.get(object.getst("GROUP"));
            if (groupname != null)
                object.set("GROUP", groupname);
        }
    }
    
    private final String getText(Map<String, String> messages, String id) {
        String text;
        
        if (messages == null)
            return id;
        text = messages.get(id);
        return (text == null)? id : text;
    }

}
