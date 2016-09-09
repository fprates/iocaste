package org.iocaste.appbuilder.common.tabletool.actions;

import java.util.Map;

import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableContextItem;
import org.iocaste.shell.common.ViewCustomAction;

public abstract class TableToolAction implements ViewCustomAction {
    private String action, name, locale;
    private TableToolData data;
    protected TableTool tabletool;
    protected boolean navigable, markable;

    public TableToolAction(TableTool tabletool, TableToolData data,
            Map<String, TableToolAction> store, String action) {
        this(tabletool, data, store, action, true);
    }
    
    public TableToolAction(TableTool tabletool, TableToolData data,
            Map<String, TableToolAction> store, String action, boolean register)
    {
        name = action.concat(data.name);
        if (register)
            tabletool.getContext().function.register(name, this);
        store.put(action, this);
        this.tabletool = tabletool;
        this.action = action;
        this.data = data;
    }
    
    public final void build(Table table) {
        TableContextItem ctxitem = table.addContextItem(action);
        ctxitem.htmlname = action.concat(data.name);
    }
    
    @Override
    public abstract void execute(AbstractContext context) throws Exception;
    
    public final String getAction() {
        return action;
    }
    
    public final String getName() {
        return name;
    }
    
    public final boolean isMarkable() {
        return markable;
    }
    
    public final boolean isNavigable() {
        return navigable;
    }
    
    protected final void setMarkable(boolean markable) {
        this.markable = markable;
    }
    
    protected final void setNavigable(boolean navigable) {
        this.navigable = navigable;
    }
    
    protected final void setText(String locale, String text) {
        if (!locale.equals(this.locale)) {
            data.context.messages.instance(locale);
            this.locale = locale;
        }
        data.context.messages.put(locale, action.concat(data.name), text);
    }
}
