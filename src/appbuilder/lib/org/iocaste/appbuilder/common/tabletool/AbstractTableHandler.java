package org.iocaste.appbuilder.common.tabletool;

import java.util.List;

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
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.TextField;

public abstract class AbstractTableHandler {
    
    protected static void additem(TableTool tabletool,
            Context context, TableToolItem ttitem, int pos) {
        Object value;
        TableToolColumn column;
        Element element;
        DataElement delement;
        InputComponent input;
        String name, paramlink, nsinput;
        Link link;
        TableItem item;
        ExtendedObject object;
        TableColumn[] tcolumns = context.table.getColumns();
        
        item = new TableItem(context.table, pos);
        if (ttitem != null)
            item.setSelected(ttitem.selected);
        
        object = (ttitem == null)? null : ttitem.object;
        nsinput = null;
        for (TableColumn tcolumn : tcolumns) {
            if (tcolumn.isMark())
                continue;
            
            name = tcolumn.getName();
            column = context.data.columns.get(name);
            delement = tcolumn.getModelItem().getDataElement();
            input = null;
            switch (delement.getType()) {
            case DataType.BOOLEAN:
                element = new CheckBox(item, name);
                break;
            default:
                switch (column.type) {
                case TEXT:
                    element = new Text(item, name);
                    break;
                case LIST_BOX:
                    input = new ListBox(item, name);
                    input.setDataElement(delement);
                    element = input;
                    if (column.values == null)
                        break;
                    
                    for (String vname : column.values.keySet())
                        ((ListBox)input).add(vname, column.values.get(vname));
                    break;
                case TEXT_FIELD:
                    input = new TextField(item, name);
                    input.setDataElement(delement);
                    element = input;
                    break;
                case LINK:
                    link = new Link(item, name, column.action);
                    element = link;
                    if (object == null)
                        break;

                    value = object.get(name);
                    if (value == null)
                        break;
                    
                    paramlink = new StringBuilder(context.data.name).
                            append(".").append(name).toString();
                    
                    link.add(paramlink, value.toString());
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
            
            if (column.disabled)
                element.setEnabled(false);

            if (tcolumn.isNamespace())
                nsinput = element.getHtmlName();
            
            if (input != null) {
                if (nsinput != null)
                    input.setNSReference(nsinput);
                else
                    input.setNSReference(context.data.nsfield);
            }
        }
        
        if (object == null)
            return;
        
        if (context.data.itemcolumn != null) {
            context.data.last += context.data.increment; 
            object.set(context.data.itemcolumn, context.data.last);
        }
        
        tabletool.set(item, object);
    }
    
    protected static final void additems(TableTool tabletool,
            Context context, List<TableToolItem> items) {
        int vlines = context.table.getVisibleLines();
        
        if ((items == null) || (items.size() == 0)) {
            if (vlines == 0)
                vlines = 15;
            
            for (int i = 0; i < vlines; i++)
                additem(tabletool, context, null, -1);
        } else {
            for (TableToolItem item : items)
                additem(tabletool, context, item, -1);
        }
    }
    
    private static final <T extends Element> T getElement(
            TableToolData data, String name) {
        return data.context.view.getElement(name.concat(data.name));
    }
    
    protected static final Table getTable(TableToolData data) {
        return data.context.view.getElement(data.name.concat("_table"));
    }
    
    /**
     * 
     * @param mode
     */
    protected static final void setMode(Context context) {
        switch (context.data.mode) {
        case TableTool.UPDATE:
        case TableTool.CONTINUOUS_UPDATE:
            context.accept.setVisible(false);
            context.add.setVisible(true);
            context.remove.setVisible(true);
            break;
            
        case TableTool.DISPLAY:
        case TableTool.CONTINUOUS_DISPLAY:
            context.accept.setVisible(false);
            context.add.setVisible(false);
            context.remove.setVisible(false);
            context.table.setEnabled(false);
            for (String column : context.data.columns.keySet())
                context.data.columns.get(column).disabled = true;
            if (context.data.enableonly == null)
                break;
            
            for (String name : context.data.enableonly)
                if (!context.data.columns.containsKey(name))
                    throw new RuntimeException(
                            name.concat(" isn't a valid column."));
                else
                    context.data.columns.get(name).disabled = false;
            break;
        }

        context.table.setMark(context.data.mark);
        if (context.data.hide != null)
            setVisibility(context, false, context.data.hide);
        if (context.data.show != null)
            setVisibility(context, true, context.data.show);
    }
    
    protected static void setObject(TableTool tabletool, TableToolData data) {
        Context extcontext = new Context();
        
        extcontext.table = getTable(data);
        extcontext.table.clear();
        extcontext.data = data;
        extcontext.data.last = 0;
        extcontext.accept = getElement(data, TableTool.ACCEPT);
        extcontext.add = getElement(data, TableTool.ADD);
        extcontext.remove = getElement(data, TableTool.REMOVE);
        extcontext.prev = getElement(data, TableTool.PREVIOUS);
        extcontext.next = getElement(data, TableTool.NEXT);
        setObjects(tabletool, extcontext);
    }
    
    /**
     * 
     * @param objects
     */
    protected static final void setObjects(TableTool tabletool, Context context)
    {
        boolean visible;
        List<TableToolItem> items = context.data.getItems();
        
        if (items.size() == 0) {
            switch(context.data.mode) {
            case TableTool.CONTINUOUS_DISPLAY:
            case TableTool.CONTINUOUS_UPDATE:
                context.table.clear();
                return;
            case TableTool.UPDATE:
            case TableTool.DISPLAY:
                additems(tabletool, context, null);
                return;
            }
        }
        
        additems(tabletool, context, items);
        visible = (items.size() > context.data.vlines);
        context.prev.setVisible(visible);
        context.next.setVisible(visible);
    }
    
    /**
     * 
     * @param context
     * @param visible
     * @param columns
     */
    private static final void setVisibility(
            Context context, boolean visible, String... columns) {
        TableColumn tcolumn;
        
        for (TableColumn column : context.table.getColumns())
            if (!column.isMark())
                column.setVisible(!visible);
        
        for (String column : columns) {
            tcolumn = context.table.getColumn(column);
            if (tcolumn == null)
                throw new RuntimeException(
                        column.concat(" is an invalid column."));
            tcolumn.setVisible(visible);
        }
    }

}

class Context {
    public TableToolData data;
    public Table table;
    public Button accept, add, remove, prev, next;
}