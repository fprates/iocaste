package org.iocaste.appbuilder.common.tabletool;

import java.util.Map;

import org.iocaste.appbuilder.common.AbstractComponentDataItem;
import org.iocaste.appbuilder.common.ComponentEntry;
import org.iocaste.appbuilder.common.ViewComponents;
import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolColumn;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Button;
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
    
    protected static TableItem additem(TableTool tabletool,
            Context context, TableToolItem ttitem, int pos) {
        TableToolColumn column;
        Element element;
        DataElement delement;
        InputComponent input;
        String name, paramlink, nsinput;
        Link link;
        Button button;
        ExtendedObject object;
        ViewComponents components;
        ComponentEntry entry;
        Table table = tabletool.getElement();
        TableColumn[] tcolumns = table.getColumns();
        TableItem item = new TableItem(table, pos);
        
        if (ttitem != null) {
            object = ttitem.object;
            ttitem.set(item);
        } else {
            object = null;
        }

        components = context.data.context.getView().getComponents();
        nsinput = null;
        for (TableColumn tcolumn : tcolumns) {
            if (tcolumn.isMark())
                continue;
            
            name = tcolumn.getName();
            column = context.data.get(name);
            delement = tcolumn.getModelItem().getDataElement();
            input = null;
            switch (delement.getType()) {
            case DataType.BOOLEAN:
                element = new CheckBox(item, name);
                break;
            default:
                switch (column.componenttype) {
                case TEXT:
                    element = new Text(item, name);
                    break;
                case LIST_BOX:
                    element = input = new ListBox(item, name);
                    input.setDataElement(delement);
                    if (column.values == null)
                        break;
                    
                    for (String vname : column.values.keySet())
                        ((ListBox)input).add(vname, column.values.get(vname));
                    break;
                case TEXT_FIELD:
                    element = input = new TextField(item, name);
                    input.setDataElement(delement);
                    break;
                case LINK:
                    element = link = new Link(item, name, column.action);
                    link.setText(null);
                    paramlink = new StringBuilder(context.data.name).
                            append(".").append(name).toString();
                    link.add(paramlink, null);
                    link.setNoScreenLock(column.nolock);
                    break;
                case BUTTON:
                    element = button = new Button(item, name);
                    button.setAction(column.action);
                    button.setText(column.text);
                    button.setNoScreenLock(column.nolock);
                    break;
                default:
                    throw new RuntimeException("component type not supported"
                            + " in this version.");
                }
            }
            
            if (object == null && context.data.itemcolumn != null && name.
                    equals(context.data.itemcolumn)) {
                context.data.last += context.data.increment;
                if (element.isDataStorable()) {
                    input = (InputComponent)element;
                    input.set(context.data.last);
                } else {
                    ((Text)element).setText(Long.toString(context.data.last));
                }
            }
            
            element.setEnabled(!column.disabled);
            if (tcolumn.isNamespace())
                nsinput = element.getHtmlName();
            
            if (input != null) {
                if (nsinput != null) {
                    input.setNSReference(nsinput);
                    continue;
                }
                
                if (context.data.nsfield != null) {
                    input.setNSReference(context.data.nsfield);
                    continue;
                }
                
                if (context.data.nsdata == null)
                    continue;
                
                entry = components.entries.get(context.data.nsdata.name);
                input.setNSReference(entry.component.getNSField());
            }
        }
        
        if (ttitem != null)
            tabletool.setLineProperties(context, tcolumns, ttitem);
        
        if (object == null) {
            tabletool.set(item, null);
            return item;
        }
        
        if (context.data.itemcolumn != null) {
            context.data.last += context.data.increment; 
            object.set(context.data.itemcolumn, context.data.last);
        }
        
        tabletool.set(item, object);
        return item;
    }
    
    protected static final void additems(TableTool tabletool,
            Context context, Map<Integer, TableToolItem> items) {
        int l, lastline;
        int vlines = context.data.vlines;
        
        if ((items == null) || (items.size() == 0)) {
            if (vlines == 0)
                vlines = 15;
            
            for (int i = 0; i < vlines; i++)
                additem(tabletool, context, null, -1);
        } else {
            l = -1;
            if (vlines == 0)
                vlines = context.data.getItems().size();
            lastline = context.data.topline + vlines - 1;
            for (int key : items.keySet()) {
                l++;
                if (l < context.data.topline)
                    continue;
                if (l > lastline)
                    break;
                additem(tabletool, context, items.get(key), -1);
            }
        }
    }
    
    /**
     * 
     * @param mode
     */
    protected static final void setMode(TableTool tabletool, Context context) {
        Table table = tabletool.getElement();
        Map<String, TableContextItem> ctxitems;
        Map<String, AbstractComponentDataItem> columns;
        
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
            columns = context.data.get();
            for (String column : columns.keySet())
                columns.get(column).disabled = true;
            break;
        }

        table.setMark(context.data.mark);
    }

    public static void setObject(TableTool tabletool, TableToolData data) {
        Context extcontext = new Context();
        Table table = tabletool.getElement();
        
        table.clear();
        extcontext.data = data;
        extcontext.data.last = 0;
        setObjects(tabletool, extcontext);
    }
    
    /**
     * 
     * @param objects
     */
    protected static final void setObjects(TableTool tabletool, Context context)
    {
        Map<Integer, TableToolItem> items = context.data.getItems();
        Table table = tabletool.getElement();
        
        tabletool.setVisibleNavigation(context, items);
        if (items.size() == 0) {
            switch(context.data.mode) {
            case TableTool.CONTINUOUS_DISPLAY:
            case TableTool.CONTINUOUS_UPDATE:
                table.clear();
                return;
            case TableTool.UPDATE:
            case TableTool.DISPLAY:
                additems(tabletool, context, null);
                return;
            }
        }
        
        additems(tabletool, context, items);
    }
    
    private static final void setVisible(Map<String, TableContextItem> ctxitems,
            String name, boolean visible) {
        if (ctxitems.containsKey(name))
            ctxitems.get(name).visible = visible;
    }

}

class Context {
    public String htmlname;
    public TableToolData data;
}