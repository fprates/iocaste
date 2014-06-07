package org.iocaste.appbuilder.common;

import org.iocaste.docmanager.common.Manager;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.InputComponent;

public abstract class AbstractActionHandler {
    private PageBuilderContext context;
    private Manager manager;
    
    protected final String dbactionget(String dashboard, String item) {
        return getViewComponents().dashboards.get(dashboard).
                get(item).getValue();
    }
    
    protected final DocumentExtractor documentExtractorInstance() {
        return new DocumentExtractor(context, manager);
    }
    
    protected final void execute(String action) throws Exception {
        String view = context.view.getPageName();
        context.getActionHandler(view, action).run(context);
    }
    
    protected abstract void execute(PageBuilderContext context)
            throws Exception;
    
    private InputComponent getdfinput(String dataform, String field) {
        DataForm form = (DataForm)context.view.getElement(dataform);
        if (form == null)
            throw new RuntimeException(dataform.concat(
                    " isn't a valid dataform."));
        return form.get(field);
    }
    
    protected final long getdfi(String dataform, String field) {
        return getdfinput(dataform, field).geti();
    }
    
    private final InputComponent getdfinputkey(String dataform) {
        for (DocumentModelKey key : manager.getModel().getHeader().getKeys())
            return getdfinput(dataform, key.getModelItemName());

        throw new RuntimeException(dataform.concat(" isn't a valid key."));
    }
    
    protected final Object getdfkey(String dataform) {
        return getdfinputkey(dataform).get();
    }
    
    protected final int getdfkeyi(String dataform) {
        return getdfinputkey(dataform).geti();
    }
    
    protected final long getdfkeyl(String dataform) {
        return getdfinputkey(dataform).getl();
    }
    
    protected final String getdfkeyst(String dataform) {
        return getdfinputkey(dataform).getst();
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
    
    @SuppressWarnings("unchecked")
    protected final <T extends ExtendedContext> T getExtendedContext() {
        return (T)context.getExtendedContext(context.view.getPageName());
    }
    
    protected final Manager getManager() {
        return manager;
    }
    
    protected final ExtendedObject[] gettcitems(String tablecontrol) {
        return getViewComponents().tabletools.get(tablecontrol).getObjects();
    }
    
    private final ViewComponents getViewComponents() {
        return context.getViewComponents(context.view.getPageName());
    }
    
    protected final boolean keyExists(Object id) {
        return manager.exists(id);
    }
    
    protected final void managerMessage(Const status, int msgid) {
        context.view.message(status, manager.getMessage(msgid));
    }
    
    public final void run(AbstractContext context) throws Exception {
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
