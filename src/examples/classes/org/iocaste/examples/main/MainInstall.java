package org.iocaste.examples.main;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.ModelInstall;
import org.iocaste.appbuilder.common.StandardInstallContext;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;

public class MainInstall extends AbstractInstallObject {

    @Override
    protected void execute(StandardInstallContext context) throws Exception {
        ModelInstall model;
        DataElement entryname, entrytext;
        
        entryname = elementchar("EXAMPLES_ENTRY_NAME", 24, DataType.KEEPCASE);
        entrytext = elementchar("EXAMPLES_ENTRY_TEXT", 64, DataType.KEEPCASE);
        
        model = modelInstance("EXAMPLES_MAIN_ENTRY");
        model.item("NAME", entryname);
        model.item("TEXT", entrytext);
    }
    
}