package org.iocaste.tasksel.groups;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.appbuilder.common.panel.PanelPageItem;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.tasksel.Main;

public class GroupsPanelPage extends AbstractPanelPage {
    public Main function;
    
    public final void execute() {
        set(new GroupsSpec());
        set(new GroupsConfig());
        set(new GroupsInput());
        put("group", new GroupsSelect());
        update();
        refresh();
    }
    
    public final void refresh() {
        PanelPageItem item;
        String name, text, groupname;
        Map<String, PanelPageItem> groups;
        
        function.page = this;
        items.clear();
        groups = new HashMap<>();
        for (ExtendedObject entry : function.entries) {
            groupname = entry.getst("GROUP");
            item = groups.get(groupname);
            if (item == null) {
                item = instance(groupname);
                groups.put(groupname, item);
            }
            
            name = entry.getst("NAME");
            text = entry.getst("TEXT");
            if (text == null)
                text = name;
            item.context.call(text, name);
        }
    }
}
