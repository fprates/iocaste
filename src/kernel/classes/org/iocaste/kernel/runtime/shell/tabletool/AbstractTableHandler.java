package org.iocaste.kernel.runtime.shell.tabletool;

import java.util.Map;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.kernel.runtime.shell.ComponentEntry;
import org.iocaste.kernel.runtime.shell.elements.Button;
import org.iocaste.runtime.common.page.ViewSpecItem.TYPES;
import org.iocaste.shell.common.CheckBox;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.ListBox;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.TableContextItem;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.TextField;

public abstract class AbstractTableHandler {
    
    protected static TableItem additem(
            TableContext context, TableToolItem ttitem, int pos) {
        TableToolColumn ttcolumn;
        Element element;
        DataElement delement;
        InputComponent input;
        String name, paramlink, nsinput, itemcolumn;
        Link link;
        Button button;
        ExtendedObject object;
        ComponentEntry entry;
        Table table = context.tabletool.getElement();
        TableColumn[] tcolumns = table.getColumns();
        TableItem item = new TableItem(table, pos);
        
        if (ttitem != null) {
            object = ttitem.object;
            ttitem.set(item);
        } else {
            object = null;
        }

        itemcolumn = context.data.getst("itemcolumn");
        nsinput = null;
        for (TableColumn tcolumn : tcolumns) {
            if (tcolumn.isMark())
                continue;
            
            name = tcolumn.getName();
            ttcolumn = context.columns.get(name);
            delement = tcolumn.getModelItem().getDataElement();
            input = null;
            switch (delement.getType()) {
            case DataType.BOOLEAN:
                element = new CheckBox(item, name);
                break;
            default:
                switch (ttcolumn.data.componenttype) {
                case TEXT:
                    element = new Text(item, name);
                    break;
                case LIST_BOX:
                    element = input = new ListBox(item, name);
                    input.setDataElement(delement);
                    if (ttcolumn.data.values == null)
                        break;
                    
                    for (String vname : ttcolumn.data.values.keySet())
                        ((ListBox)input).add(
                                vname, ttcolumn.data.values.get(vname));
                    break;
                case TEXT_FIELD:
                    element = input = new TextField(item, name);
                    input.setDataElement(delement);
                    break;
                case LINK:
                    element = link = new Link(
                            item, name, ttcolumn.data.actionname);
                    link.setText(null);
                    paramlink = new StringBuilder(context.data.name).
                            append(".").append(name).toString();
                    link.add(paramlink, null);
                    link.setNoScreenLock(ttcolumn.data.nolock);
                    break;
                case BUTTON:
                    context.viewctx.instance(TYPES.BUTTON, name);
                    element = button = new Button(context.viewctx, name);
                    button.setAction(ttcolumn.data.actionname);
                    button.setText(ttcolumn.data.text);
                    button.setNoScreenLock(ttcolumn.data.nolock);
                    break;
                default:
                    throw new RuntimeException("component type not supported"
                            + " in this version.");
                }
            }
            
            if (object == null && itemcolumn != null && name.equals(itemcolumn))
            {
                context.last += context.data.geti("increment");
                if (element.isDataStorable()) {
                    input = (InputComponent)element;
                    input.set(context.last);
                } else {
                    ((Text)element).setText(Long.toString(context.last));
                }
            }
            
            element.setEnabled(!ttcolumn.data.disabled);
            if (tcolumn.isNamespace())
                nsinput = element.getHtmlName();
            
            if (input != null) {
                if (nsinput != null) {
                    input.setNSReference(nsinput);
                    continue;
                }
                
                if (context.data.nsitem != null) {
                    input.setNSReference(context.data.nsitem.name);
                    continue;
                }
                
                if (context.data.nsdata == null)
                    continue;
                
                entry = context.viewctx.entries.get(context.data.nsdata.name);
                input.setNSReference(entry.component.getNSField());
            }
        }
        
        if (ttitem != null)
            context.tabletool.setLineProperties(tcolumns, ttitem);
        
        if (object == null) {
            context.tabletool.set(item, null);
            return item;
        }
        
        if (itemcolumn != null) {
            context.last += context.data.geti("increment"); 
            object.set(itemcolumn, context.last);
        }
        
        context.tabletool.set(item, object);
        return item;
    }
    
    protected static final void additems(TableContext context) {
        int l, lastline;
        int vlines = context.data.vlength;
        
        if (context.items.size() == 0) {
            if (vlines == 0)
                vlines = 15;
            
            for (int i = 0; i < vlines; i++)
                additem(context, null, -1);
        } else {
            l = -1;
            if (vlines == 0)
                vlines = context.data.items.size();
            lastline = context.data.geti("topline") + vlines - 1;
            for (int key : context.items.keySet()) {
                l++;
                if (l < context.data.geti("topline"))
                    continue;
                if (l > lastline)
                    break;
                additem(context, context.items.get(key), -1);
            }
        }
    }
    
    /**
     * 
     * @param mode
     */
    protected static final void setMode(TableContext context) {
        Table table = context.tabletool.getElement();
        Map<String, TableContextItem> ctxitems;
        
        ctxitems = table.getContextItems();
        switch (context.data.mode) {
        case TableTool.UPDATE:
        case TableTool.CONTINUOUS_UPDATE:
            setVisible(ctxitems, "accept", false);
            setVisible(ctxitems, "add", true);
            setVisible(ctxitems, "remove", true);
            break;
            
        case TableTool.DISPLAY:
        case TableTool.CONTINUOUS_DISPLAY:
            setVisible(ctxitems, "accept", false);
            setVisible(ctxitems, "add", false);
            setVisible(ctxitems, "remove", false);
            table.setEnabled(false);
            for (String column : context.columns.keySet())
                context.columns.get(column).data.disabled = true;
            break;
        }

        table.setMark(context.data.getbl("mark"));
    }

    public static void setObject(TableContext context) {
        Table table = context.tabletool.getElement();
        table.clear();
        context.last = 0;
        setObjects(context);
    }
    
    /**
     * 
     * @param objects
     */
    protected static final void setObjects(TableContext context) {
        Table table = context.tabletool.getElement();
        
        context.tabletool.setVisibleNavigation();
        if (context.items.size() == 0) {
            switch(context.data.mode) {
            case TableTool.CONTINUOUS_DISPLAY:
            case TableTool.CONTINUOUS_UPDATE:
                table.clear();
                return;
            case TableTool.UPDATE:
            case TableTool.DISPLAY:
                additems(context);
                return;
            }
        }
        
        additems(context);
    }
    
    private static final void setVisible(Map<String, TableContextItem> ctxitems,
            String name, boolean visible) {
        if (ctxitems.containsKey(name))
            ctxitems.get(name).visible = visible;
    }

}
