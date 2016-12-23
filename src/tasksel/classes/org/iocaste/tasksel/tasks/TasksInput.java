package org.iocaste.tasksel.tasks;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.appbuilder.common.AbstractViewInput;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.tasksel.Context;

public class TasksInput extends AbstractViewInput {

    @Override
    protected void execute(PageBuilderContext context) {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void init(PageBuilderContext context) {
        List<ExtendedObject> items = new ArrayList<>();
        Context extcontext = getExtendedContext();
        
        for (ExtendedObject entry : extcontext.entries)
            if (entry.getst("GROUP").equals(extcontext.group))
                items.add(entry);
        
        tilesset("items", items);
    }
    
}