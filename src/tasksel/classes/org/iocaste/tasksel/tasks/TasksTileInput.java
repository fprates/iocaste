package org.iocaste.tasksel.tasks;

import org.iocaste.appbuilder.common.AbstractViewInput;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ExtendedObject;

public class TasksTileInput extends AbstractViewInput {

    @Override
    protected void execute(PageBuilderContext context) {
        ExtendedObject object = tilesobjectget("items");
        
        textset("text", object.getst("TEXT"));
        textset("tileitem", "");
        linkadd("tileitem", object, "NAME");
    }

    @Override
    protected void init(PageBuilderContext context) {
        execute(context);
    }
    
}