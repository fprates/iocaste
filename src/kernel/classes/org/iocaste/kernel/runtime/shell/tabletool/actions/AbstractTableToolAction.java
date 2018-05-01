package org.iocaste.kernel.runtime.shell.tabletool.actions;

import java.util.Map;
import java.util.Set;

import org.iocaste.kernel.runtime.shell.tabletool.TableContext;
import org.iocaste.shell.common.AbstractEventHandler;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableContextItem;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.tooldata.MetaObject;
import org.iocaste.shell.common.tooldata.ToolData;

public abstract class AbstractTableToolAction extends AbstractEventHandler {
    private static final long serialVersionUID = -7712067205074943704L;
    private String action, name;
    protected TableContext context;
    protected boolean navigable, markable;

    public AbstractTableToolAction(TableContext context,
            Map<String, AbstractTableToolAction> store, String action) {
        this(context, store, action, true);
    }
    
    public AbstractTableToolAction(TableContext context,
            Map<String, AbstractTableToolAction> store, String action,
            boolean register) {
        name = action.concat(context.name);
        store.put(action, this);
        this.context = context;
        this.action = action;
    }
    
    public final void build(ToolData data, Table table) {
        TableContextItem ctxitem = table.addContextItem(action);
        ctxitem.htmlname = action.concat(data.name);
    }
    
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
    
    protected final void move() {
//        int l, lastline;
//        Set<TableItem> items;
//        Table table = context.tabletool.getElement();
//        
//        items = table.getItems();
//        l = context.data.vlength - items.size();
//        if (l > 0)
//            for (int i= 0; i < l; i++)
//                items.add(addLine(null, -1));
//        l = context.data.topline;
//        lastline = context.data.objects.size() - 1;
//        for (TableItem item : items) {
//            if (l > lastline) {
//                set(item, null);
//                item.setVisible(false);
//                continue;
//            }
//
//            item.setVisible(true);
//            set(item, context.data.objects.get(l));
//            l++;
//        }
    }

    protected final void save(ToolData data) {
        MetaObject ttitem;
        int l = data.topline;
        Table table = context.tabletool.getElement();
        Set<TableItem> items = table.getItems();
        
        for (TableItem item : items) {
            ttitem = data.objects.get(l);
            if (ttitem == null)
                data.objects.put(l, ttitem = new MetaObject(null));
            ttitem.selected = item.isSelected();
            l++;
        }
    }
    
    protected final void setMarkable(boolean markable) {
        this.markable = markable;
    }
    
    protected final void setNavigable(boolean navigable) {
        this.navigable = navigable;
    }
    
    protected final void setText(String locale, String text) {
        context.viewctx.messagesrc.instance(locale);
        context.viewctx.messagesrc.put(name, text);
    }
}
