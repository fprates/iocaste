package org.iocaste.appbuilder.common;

import org.iocaste.docmanager.common.Manager;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.ViewCustomAction;

public abstract class AbstractActionHandler implements ViewCustomAction {
    private static final long serialVersionUID = 4793050042995085189L;
    private PageBuilderContext context;
    private Manager manager;
    
    protected DocumentExtractor documentExtractorInstance() {
        return new DocumentExtractor(context);
    }
    
    protected abstract void execute(PageBuilderContext context);
    
    @Override
    public final void execute(AbstractContext context) {
        String view = context.view.getPageName();
        String action = context.view.getActionControl();
        
        this.context = (PageBuilderContext)context;
        if (!this.context.hasActionHandler(view, action))
            return;
        
        execute(this.context);
    }
    
    protected final long getdfi(String dataform, String field) {
        DataForm form = context.view.getElement(dataform);
        return form.get(field).geti();
    }
    
    protected final long getdfl(String dataform, String field) {
        DataForm form = context.view.getElement(dataform);
        return form.get(field).getl();
    }
    
    protected final String getdfst(String dataform, String field) {
        DataForm form = context.view.getElement(dataform);
        return form.get(field).getst();
    }
    
    protected final ComplexDocument getDocument(Object id) {
        return manager.get(id);
    }
    
    protected final void managerMessage(Const status, int msgid) {
        context.view.message(status, manager.getMessage(msgid));
    }
    
    public final void setManager(Manager manager) {
        this.manager = manager;
    }
    
    protected final void setParameter(String parameter, Object value) {
        context.setParameter(parameter, value);
    }
}
