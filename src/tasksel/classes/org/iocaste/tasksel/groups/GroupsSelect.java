package org.iocaste.tasksel.groups;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.tasksel.Context;

public class GroupsSelect extends AbstractActionHandler {

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        Context extcontext = getExtendedContext();
        String group = getinputst("item_GROUP");
        
        extcontext.pagetiles.get("tasks").setTitle(group);
        
        init("tasks", extcontext);
        extcontext.tilesInstance("tasks", "items");
        extcontext.tilesclear("tasks", "items");
        for (ExtendedObject object : extcontext.tasks)
            if (object.getst("GROUP").equals(group))
                extcontext.tilesadd("tasks", "items", object);
        redirect("tasks");
    }

}
