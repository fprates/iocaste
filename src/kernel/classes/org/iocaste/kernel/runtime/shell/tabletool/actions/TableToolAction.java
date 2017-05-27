package org.iocaste.kernel.runtime.shell.tabletool.actions;

import java.util.Map;

import org.iocaste.kernel.runtime.shell.tabletool.TableContext;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableContextItem;
import org.iocaste.shell.common.ViewCustomAction;

public abstract class TableToolAction implements ViewCustomAction {
    private String action, name, locale;
    protected TableContext context;
    protected boolean navigable, markable;

    public TableToolAction(TableContext context,
            Map<String, TableToolAction> store, String action) {
        this(context, store, action, true);
    }
    
    public TableToolAction(TableContext context,
            Map<String, TableToolAction> store, String action, boolean register)
    {
        name = action.concat(context.data.name);
//        if (register)
//            tabletool.getContext().function.register(name, this);
        store.put(action, this);
        this.context = context;
        this.action = action;
    }
    
    public final void build(Table table) {
        TableContextItem ctxitem = table.addContextItem(action);
        ctxitem.htmlname = action.concat(context.data.name);
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
            context.viewctx.messagesrc.instance(locale);
            this.locale = locale;
        }
        context.viewctx.messagesrc.put(
                locale, action.concat(context.data.name), text);
    }
}
