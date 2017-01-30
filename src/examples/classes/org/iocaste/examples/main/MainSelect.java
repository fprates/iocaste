package org.iocaste.examples.main;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.examples.Context;

public class MainSelect extends AbstractActionHandler {
    private static final String[][] entries = {
        {"hello-world", "Olá, Mundo"},
        {"dataform-use", "Preenchimento de dataform"}
    };
    
    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        ExtendedObject object;
        Context extcontext = getExtendedContext();
        
        init("main", extcontext);
        extcontext.tilesInstance("main", "items");
        extcontext.tilesclear("main", "items");
        for (int i = 0; i < entries.length; i++) {
            object = instance("EXAMPLES_MAIN_ENTRY");
            object.set("NAME", entries[i][0]);
            object.set("TEXT", entries[i][1]);
            extcontext.tilesadd("main", "items", object);
        }
            
    }
    
}