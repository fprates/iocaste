package org.iocaste.appbuilder.common.cmodelviewer;

import java.util.Collection;

import org.iocaste.appbuilder.common.AbstractTableToolHandler;
import org.iocaste.appbuilder.common.ExtendedContext;
import org.iocaste.documents.common.ExtendedObject;

public class CModelHandler extends AbstractTableToolHandler {

    public CModelHandler(ExtendedContext extcontext) {
        super(extcontext);
        extcontext.setDataHandler(this, "head", "base");
    }
    
    @Override
    public final void add(String ttname, ExtendedObject[] items) {
        CModelViewerContext cmodelctx = (CModelViewerContext)extcontext;
        
        if (cmodelctx.document == null)
            return;
        cmodelctx.document.remove(cmodelctx.models.get(ttname));
        cmodelctx.document.add(items);
    }
    
    @Override
    public final void add(String ttname, Collection<ExtendedObject> items) {
        CModelViewerContext cmodelctx = (CModelViewerContext)extcontext;
        
        if (cmodelctx.document == null)
            return;
        cmodelctx.document.remove(cmodelctx.models.get(ttname));
        cmodelctx.document.add(items);
    }
    
    @Override
    public final void add(String ttname, ExtendedObject object) {
        CModelViewerContext cmodelctx = (CModelViewerContext)extcontext;
        cmodelctx.document.add(object);
    }
    
    @Override
    public ExtendedObject get() {
        CModelViewerContext cmodelctx = (CModelViewerContext)extcontext;
        return (!isInitialized())? null : cmodelctx.document.getHeader();
    }
    
    @Override
    public ExtendedObject[] get(String ttname) {
        CModelViewerContext cmodelctx = (CModelViewerContext)extcontext;
        return (!isInitialized())? null : cmodelctx.document.getItems(
                cmodelctx.models.get(ttname));
    }

    @Override
    public final boolean isInitialized() {
        CModelViewerContext cmodelctx = (CModelViewerContext)extcontext;
        if (cmodelctx == null)
            return false;
        return (cmodelctx.document != null);
    }
    
    @Override
    public final void remove(String ttname, ExtendedObject object) {
        CModelViewerContext cmodelctx = (CModelViewerContext)extcontext;
        cmodelctx.document.remove(object);
    }
    
    @Override
    public final void set(String dfname, ExtendedObject object) {
        CModelViewerContext cmodelctx = (CModelViewerContext)extcontext;
        cmodelctx.document.setHeader(object);
    }
}
