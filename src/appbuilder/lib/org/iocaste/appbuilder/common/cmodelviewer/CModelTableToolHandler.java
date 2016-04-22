package org.iocaste.appbuilder.common.cmodelviewer;

import org.iocaste.appbuilder.common.AbstractTableToolHandler;
import org.iocaste.documents.common.ExtendedObject;

public class CModelTableToolHandler extends AbstractTableToolHandler {

    @Override
    public void action(String ttname, ExtendedObject[] items) {
        Context cmodelctx = (Context)extcontext;
        
        if (cmodelctx.document == null)
            return;
        cmodelctx.document.remove(cmodelctx.models.get(ttname));
        cmodelctx.document.add(items);
    }
    
    @Override
    public ExtendedObject[] input(String ttname) {
        Context cmodelctx = (Context)extcontext;
        
        return (cmodelctx.document == null)? null : cmodelctx.document.getItems(
                cmodelctx.models.get(ttname));
    }

}
