package org.iocaste.tasksel.groups;

import org.iocaste.appbuilder.common.AbstractViewInput;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ExtendedObject;

public class GroupsTileInput extends AbstractViewInput {

    @Override
    protected void execute(PageBuilderContext context) {
        ExtendedObject object = tilesobjectget();
        
        textset("name", object.getst("GROUP"));
        textset("tileitem", "");
        linkadd("tileitem", object, "GROUP");
    }

    @Override
    protected void init(PageBuilderContext context) {
        execute(context);
    }
    
}