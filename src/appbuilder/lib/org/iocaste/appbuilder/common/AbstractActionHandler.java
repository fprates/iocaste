package org.iocaste.appbuilder.common;

import org.iocaste.docmanager.common.Manager;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.InputComponent;

public abstract class AbstractActionHandler {
    private PageBuilderContext context;
    private Manager manager;
    
    protected DocumentExtractor documentExtractorInstance() {
        return new DocumentExtractor(context, manager);
    }
    
    protected abstract void execute(PageBuilderContext context);
    
    private InputComponent getdfinput(String dataform, String field) {
        return ((DataForm)context.view.getElement(dataform)).get(field);
    }
    
    protected final long getdfi(String dataform, String field) {
        return getdfinput(dataform, field).geti();
    }
    
    private final InputComponent getdfinputkey(String dataform) {
        for (DocumentModelKey key : manager.getModel().getHeader().getKeys())
            return getdfinput(dataform, key.getModelItemName());
        
        return null;
    }
    
    protected final Object getdfkey(String dataform) {
        InputComponent input = getdfinputkey(dataform);
        return (input == null)? null : input.get();
    }
    
    protected final String getdfkeyst(String dataform) {
        InputComponent input = getdfinputkey(dataform);
        return (input == null)? null : input.getst();
    }
    
    protected final long getdfl(String dataform, String field) {
        return getdfinput(dataform, field).getl();
    }
    
    protected final String getdfst(String dataform, String field) {
        return getdfinput(dataform, field).getst();
    }
    
    protected final ComplexDocument getDocument(Object id) {
        return manager.get(id);
    }
    
    protected final Manager getManager() {
        return manager;
    }
    
    protected final boolean keyExists(Object id) {
        return manager.exists(id);
    }
    
    protected final void managerMessage(Const status, int msgid) {
        context.view.message(status, manager.getMessage(msgid));
    }
    
    public final void run(AbstractContext context) {
        String view = context.view.getPageName();
        String action = context.view.getActionControl();
        
        this.context = (PageBuilderContext)context;
        if (!this.context.hasActionHandler(view, action))
            return;
        
        execute(this.context);
    }
    
    public final void setManager(Manager manager) {
        this.manager = manager;
    }
}
