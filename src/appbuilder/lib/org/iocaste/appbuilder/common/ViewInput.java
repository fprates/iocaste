package org.iocaste.appbuilder.common;

import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.TableTool;

public abstract class ViewInput {
    private PageBuilderContext context;
    
    protected final void addTableItems(String table) {
        TableTool tabletool = context.getViewComponents(
                context.view.getPageName()).tabletools.get(table);
        
        if (tabletool == null)
            throw new RuntimeException(table.
                    concat(" is an invalid tabletool."));
        
        tabletool.additems();
    }
    
    protected abstract void execute(PageBuilderContext context);
    
    public final void run(PageBuilderContext context) {
        this.context = context;
        execute(context);
    }
    
    protected final void set(String form, String item, Object value) {
        ((DataForm)context.view.getElement(form)).get(item).set(value);
    }
}
