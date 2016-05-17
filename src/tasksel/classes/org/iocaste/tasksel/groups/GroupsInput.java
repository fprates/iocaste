package org.iocaste.tasksel.groups;

import java.util.LinkedHashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.AbstractViewInput;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.tasksel.Main;

public class GroupsInput extends AbstractViewInput {

    @Override
    protected void execute(PageBuilderContext context) {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void init(PageBuilderContext context) {
        String groupname;
        Map<String, ExtendedObject> groups;
        Main main = (Main)context.function;
        
        groups = new LinkedHashMap<>();
        for (ExtendedObject entry : main.entries) {
            groupname = entry.getst("GROUP");
            if (!groups.containsKey(groupname))
                groups.put(groupname, entry);
        }
        
        tilesset("items", groups.values());
    }
    
}