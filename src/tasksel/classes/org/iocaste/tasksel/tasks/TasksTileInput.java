package org.iocaste.tasksel.tasks;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.tiles.AbstractTileInput;
import org.iocaste.documents.common.ExtendedObject;

public class TasksTileInput extends AbstractTileInput {

    @Override
    protected void execute(PageBuilderContext context) {
        ExtendedObject object = get();
        
        textset("text", object.getst("TEXT"));
        textset("tileitem", "");
        linkadd("tileitem", object, "NAME");
    }

    @Override
    protected void init(PageBuilderContext context) {
        execute(context);
    }
    
}