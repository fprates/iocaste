package org.iocaste.appbuilder.common.cmodelviewer;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class InputValidate extends AbstractActionHandler {

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        refresh();
    }
    
    private final void refresh() {
        Context extcontext;
        
        extcontext = getExtendedContext();
        for (String name : extcontext.dataforms.keySet())
            extcontext.dataforms.put(name, getdf(name));
        for (String name : extcontext.tabletools.keySet())
            extcontext.set(name, tableitemsget(name));
    }
    
}