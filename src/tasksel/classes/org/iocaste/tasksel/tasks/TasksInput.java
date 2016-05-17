package org.iocaste.tasksel.tasks;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.appbuilder.common.AbstractViewInput;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.tasksel.Context;
import org.iocaste.tasksel.Main;

public class TasksInput extends AbstractViewInput {

    @Override
    protected void execute(PageBuilderContext context) {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void init(PageBuilderContext context) {
        Main main = (Main)context.function;
        List<ExtendedObject> items = new ArrayList<>();
        Context extcontext = getExtendedContext();
        
        for (ExtendedObject entry : main.entries)
            if (entry.getst("GROUP").equals(extcontext.group))
                items.add(entry);
        
        tilesset("items", items);
    }
    
}