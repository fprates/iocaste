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
        Map<String, String> _messages;
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
                "USER_TASKS_GROUPS.PACKAGE"
        );
        query.setModel("USER_TASKS_GROUPS");
        query.join("TASK_ENTRY", "USER_TASKS_GROUPS.GROUP", "GROUP");
        query.andEqual("USERNAME", username);
        result = select(query);
        if (result == null)
            return;
        
        language = iocaste.getLocale().toString();
        extcontext.entries = new LinkedHashSet<>();
        messages = new HashMap<>();
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
            
            entry = instance("TASK_TILE_ENTRY");
            entry.set("GROUP", getText(_messages, groupname));
            entry.set("NAME", taskname);
            entry.set("TEXT", getText(_messages, taskname));
            extcontext.entries.add(entry);
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
