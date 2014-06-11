package org.iocaste.appbuilder.common;

import org.iocaste.docmanager.common.Manager;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;

public abstract class AbstractActionHandler {
    private PageBuilderContext context;
    private Manager manager;
    
    protected final void back() {
        ((AbstractPage)context.function).back();
    }
    
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
    
    protected <T> T getdf(String dataform, String field) {
        return getdfinput(dataform, field).get();
    }
    
    protected final long getdfi(String dataform, String field) {
        return getdfinput(dataform, field).geti();
    }
    
    private InputComponent getdfinput(String dataform, String field) {
        InputComponent input;
        DataForm form = (DataForm)context.view.getElement(dataform);
        
        if (form == null)
            throw new RuntimeException(dataform.concat(
                    " isn't a valid dataform."));
        
        input = form.get(field);        
        if (input == null)
            throw new RuntimeException(field.concat(" isn't a valid field."));
        
        return input;
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

    protected final String getinputst(String name) {
        return ((InputComponent)context.view.getElement(name)).getst();
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
    
    protected final void message(Const type, String text) {
        context.view.message(type, text);
    }
    
    protected final void redirect(String page) {
        context.view.redirect(page);
    }
    
    protected final void redirect(String app, String page) {
        context.view.redirect(app, page);
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
    
    private final void store(String view, String name, Object value)
    {
        context.getViewInput(view).store(name, value);
    }
    
    private final void storeitem(String view, ViewSpecItem item) {
        String name;
        DataForm dataform;
        Element element;
        InputComponent input;
        
        name = item.getName();
        
        switch (ViewSpecItem.TYPES.values()[item.getType()]) {
        case DATA_FORM:
            dataform = context.view.getElement(name);
            store(view, name, dataform.getObject());
            return;
        case TABLE_TOOL:
            store(view, name, gettcitems(name));
            return;
        default:
            element = context.view.getElement(name);
            /*
             * algum assistente não suportado, como TableTool, Dashboard, etc.
             */
            if (element == null)
                return;
            
            if (element.isDataStorable()) {
                input = (InputComponent)element;
                store(view, name, input.get());
                return;
            }
            
            if (!element.isContainable())
                return;
            break;
        }
        
        for (ViewSpecItem child : item.getItems())
            storeitem(view, child);
    }
    
    protected final void updateView() {
        AbstractViewSpec spec;
        String view = context.view.getPageName();
        
        spec = context.getViewSpec(view);
        context.getViewInput(view).setUpdated(true);
        
        for (ViewSpecItem item : spec.getItems())
            storeitem(view, item);
        
        context.view.setReloadableView(true);
    }
}
