package org.iocaste.appbuilder.common.cmodelviewer;

import org.iocaste.appbuilder.common.AbstractTableToolHandler;
import org.iocaste.appbuilder.common.ExtendedContext;
import org.iocaste.documents.common.ExtendedObject;

public class CModelHandler extends AbstractTableToolHandler {

    public CModelHandler(ExtendedContext extcontext) {
        super(extcontext);
        extcontext.setDataHandler(this, new String[] {"head", "base"}, null);
    }
    
    @Override
    public final void add(String ttname, ExtendedObject[] items) {
        Context cmodelctx = (Context)extcontext;
        
        if (cmodelctx.document == null)
            return;
        cmodelctx.document.remove(cmodelctx.models.get(ttname));
        cmodelctx.document.add(items);
    }
    
    @Override
    public final void add(String ttname, ExtendedObject object) {
        Context cmodelctx = (Context)extcontext;
        cmodelctx.document.add(object);
    }
    
    @Override
    public ExtendedObject get() {
        Context cmodelctx = (Context)extcontext;
        return (!isInitialized())? null : cmodelctx.document.getHeader();
    }
    
    @Override
    public ExtendedObject[] get(String ttname) {
        Context cmodelctx = (Context)extcontext;
        return (!isInitialized())? null : cmodelctx.document.getItems(
                cmodelctx.models.get(ttname));
    }

    @Override
    public final boolean isInitialized() {
        Context cmodelctx = (Context)extcontext;
        if (cmodelctx == null)
            return false;
        return (cmodelctx.document != null);
    }
    
    @Override
    public final void set(String dfname, ExtendedObject object) {
        Context cmodelctx = (Context)extcontext;
        cmodelctx.document.setHeader(object);
    }
}
