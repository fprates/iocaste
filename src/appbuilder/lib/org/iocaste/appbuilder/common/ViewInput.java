package org.iocaste.appbuilder.common;

import org.iocaste.shell.common.DataForm;

public abstract class ViewInput {
    private PageBuilderContext context;
    
    protected final void addTableItems(String table) {
        context.getViewComponents(context.view.getPageName()).tabletools.get(
                table).additems();
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
