package org.iocaste.appbuilder.common;

import org.iocaste.docmanager.common.Manager;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.TableTool;

public abstract class AbstractViewInput {
    private PageBuilderContext context;
    private Manager manager;
    
    protected final void addTableItems(String table) {
        TableTool tabletool = context.getViewComponents(
                context.view.getPageName()).tabletools.get(table);
        
        if (tabletool == null)
            throw new RuntimeException(table.
                    concat(" is an invalid tabletool."));
        
        tabletool.additems();
    }
    
    private InputComponent getinput(String form, String item) {
        return ((DataForm)context.view.getElement(form)).get(item);
    }
    
    protected abstract void execute(PageBuilderContext context);
    
    public final void run(PageBuilderContext context) {
        this.context = context;
        execute(context);
    }
    
    protected final void set(String form, String item, Object value) {
        getinput(form, item).set(value);
    }
    
    protected final void setdfkey(String form, Object value) {
        DocumentModel model = manager.getModel().getHeader();
        
        for (DocumentModelKey key : model.getKeys()) {
            getinput(form, key.getModelItemName()).set(value);
            break;
        }
    }
    
    public final void setManager(Manager manager) {
        this.manager = manager;
    }
}
