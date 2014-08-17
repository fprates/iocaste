package org.iocaste.appbuilder.common;

import org.iocaste.appbuilder.common.dashboard.DashboardComponent;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.docmanager.common.Manager;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.texteditor.common.TextEditor;

public abstract class AbstractViewInput {
    private PageBuilderContext context;
    
    private final void addtableitems(String table, ExtendedObject[] objects) {
        TableToolData tabletool = getViewComponents().tabletools.
                get(table).data;
        
        if (tabletool == null)
            throw new RuntimeException(table.
                    concat(" is an invalid tabletool."));
        
        tabletool.objects = objects;
    }
    
    private DashboardComponent dbget(String dashboard, String name) {
        return getViewComponents().dashboards.get(dashboard).get(name);
    }
    
    protected void dbitemadd(String dashboard, String name, String value) {
        dbget(dashboard, name).add(value);
    }
    
    protected void dbtextadd(String dashboard, String name, String value) {
        dbget(dashboard, name).addText(value);
    }
    
    protected abstract void execute(PageBuilderContext context);
    
    protected <T extends Element> T getElement(String name) {
        return context.view.getElement(name);
    }
    
    
    @SuppressWarnings("unchecked")
    protected final <T extends ExtendedContext> T getExtendedContext() {
        return (T)context.getExtendedContext(context.view.getPageName());
    }
    
    private InputComponent getinput(String form, String item) {
        return ((DataForm)context.view.getElement(form)).get(item);
    }
    
    protected final Manager getManager(String name) {
        return context.getManager(name);
    }
    
    private final ViewComponents getViewComponents() {
        return context.getViewComponents(context.view.getPageName());
    }
    
    protected final void reportset(String report, ExtendedObject[] items) {
        getViewComponents().reporttools.get(report).setItens(items);
    }
    
    public final void run(PageBuilderContext context) {
        this.context = context;
        execute(context);
    }
    
    protected final void set(String form, String item, Object value) {
        getinput(form, item).set(value);
    }
    
    protected final void setdf(String form, ExtendedObject object) {
        ((DataForm)context.view.getElement(form)).setObject(object);
    }
    
    protected final void setdfkey(String form, Object value) {
        DataForm df = context.view.getElement(form);
        DocumentModel model = df.getModel();
        
        for (DocumentModelKey key : model.getKeys()) {
            getinput(form, key.getModelItemName()).set(value);
            break;
        }
    }
    
    protected final void tableitemsadd(String table) {
        addtableitems(table, null);
    }
    
    protected final void tableitemsadd(String table, ExtendedObject[] objects) {
        addtableitems(table, objects);
    }
    
    protected final void tableitemsadd(String table, DataConversion conversion)
    {
        Documents documents = new Documents(context.function);
        ExtendedObject[] objects = DocumentExtractor.extractItems(
                documents, conversion, null, null);
        
        addtableitems(table, objects);
    }
    
    protected final void texteditorset(String texteditor, String text) {
        TextEditor editor = getViewComponents().editors.get(texteditor);
        editor.getElement().set(text);
    }
}
