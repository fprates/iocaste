package org.iocaste.appbuilder;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolColumn;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.CheckBox;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.ListBox;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.TextField;
import org.iocaste.shell.common.View;

public class TableToolRenderer extends AbstractFunction {
    
    public TableToolRenderer() {
        export("add_action", "addaction");
        export("items_add", "addItems");
        export("multiple_objects_set", "setMultipleObjects");
        export("objects_set", "setObjects");
        export("render", "render");
    }
    
    public final TableToolData addaction(Message message) {
        int i = 0;
        Context context = new Context();
        
        context.data = message.get("data");
        context.table = getTable(context.data);
        
        switch (context.data.mode) {
        case TableTool.CONTINUOUS_UPDATE:
            for (TableItem item_ : context.table.getItems()) {
                if (!item_.isSelected()) {
                    i++;
                    continue;
                }
                break;
            }
            
            additem(context, null, i);
            break;
        default:
            additems(context, null);
            break;
        }
        
        return context.data;
    }
    
    private void additem(Context context, ExtendedObject object, int pos) {
        TableToolColumn column;
        Element element;
        DataElement delement;
        InputComponent input;
        String name;
        TableItem item = new TableItem(context.table, pos);
        TableColumn[] tcolumns = context.table.getColumns();
        
        for (TableColumn tcolumn : tcolumns) {
            if (tcolumn.isMark())
                continue;

            name = tcolumn.getName();
            column = context.data.columns.get(name);
            delement = tcolumn.getModelItem().getDataElement();
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
                    element = input;
                    if (column.values == null)
                        break;
                    
                    for (String vname : column.values.keySet())
                        ((ListBox)input).add(vname, column.values.get(vname));
                    break;
                case TEXT_FIELD:
                    element = new TextField(item, name);
                    break;
                case LINK:
                    element = new Link(item, name, column.action);
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
        }
        
        if (object == null)
            return;
        
        if (context.data.itemcolumn != null) {
            context.data.last += context.data.increment; 
            object.set(context.data.itemcolumn, context.data.last);
        }
        
        item.setObject(object);
    }
    
    private final void additems(Context context, ExtendedObject[] items) {
        int vlines = context.table.getVisibleLines();
        int total = context.table.size();
        
        if (items == null) {
            if (vlines == 0)
                vlines = 15;
            
            for (int i = 0; i < vlines; i++)
                additem(context, null, -1);
        } else {
            for (int i = 0; i < items.length; i++) {
                if ((vlines == i) && (vlines > 0))
                    break;
                
                additem(context, items[i], -1);
            }
        }
        
        context.table.setTopLine(total);
    }
    
    public final Map<String, Object> addItems(Message message) {
        Map<String, Object> result;
        Context context = new Context();
        
        context.data = message.get("data");
        context.table = getTable(context.data);
        additems(context, context.data.objects);
        
        result = new HashMap<>();
        result.put("table", context.table);
        result.put("data", context.data);
        return result;
    }
    
    private final Table getTable(TableToolData data) {
        return data.getContainer().getElement(data.name.concat("_table"));
    }
    
    /**
     * 
     * @param modelname
     */
    private final void model(Context context) {
        DocumentModelItem modelitem;
        String name;
        TableToolColumn column;
        DocumentModel model = new Documents(this).getModel(context.data.model);
        
        if (model == null)
            throw new RuntimeException(context.data.model.
                    concat(" is an invalid model."));

        context.table.importModel(model);
        for (TableColumn tcolumn : context.table.getColumns()) {
            if (tcolumn.isMark())
                continue;
            
            name = tcolumn.getName();
            column = context.data.columns.get(name);
            if (column == null)
                column = new TableToolColumn(context.data, name);
            
            column.tcolumn = tcolumn;
            modelitem = tcolumn.getModelItem();
            if (modelitem.getSearchHelp() == null)
                modelitem.setSearchHelp(column.sh);
            if (column.size > 0)
                column.tcolumn.setLength(column.size);
        }
    }
    
    public final Map<String, Object> render(Message message) {
        Map<String, Object> result;
        Container container;
        Context context = new Context();
        
        context.data = message.get("data");  
        context.view = message.get("view");
        container = context.data.getContainer();
        
        context.accept = new Button(
                container, TableTool.ACCEPT.concat(context.data.name));
        context.add = new Button(
                container, TableTool.ADD.concat(context.data.name));
        context.remove = new Button(container,
                TableTool.REMOVE.concat(context.data.name));
        
        context.table = new Table(
                container, context.data.name.concat("_table"));
        context.table.setVisibleLines(context.data.vlines);
        context.table.setHeader(!context.data.noheader);
        context.data.last = 0;
        
        model(context);
        setMode(context);
        setObjects(context);
        
        result = new HashMap<>();
        result.put("container", container);
        result.put("data", context.data);
        return result;
    }
    
    /**
     * 
     * @param mode
     */
    private final void setMode(Context context) {
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
    
    public final Map<String, TableToolData> setMultipleObjects(
            Message message) {
        Table table;
        TableToolData data;
        Map<String, TableToolData> tables = message.get("tables");
        
        for (String name : tables.keySet()) {
            data = tables.get(name);
            table = getTable(data);
            setObject(table, data);
        }
        
        return tables;
    }
    
    private final Map<String, Object> setObject(Table table, TableToolData data)
    {
        Map<String, Object> result;
        Context context = new Context();
        
        context.table = table;
        context.table.clear();
        context.data = data;
        context.data.last = 0;
        
        setObjects(context);
        
        result = new HashMap<>();
        result.put("table", context.table);
        result.put("data", context.data);
        return result;
    }
    
    public final Map<String, Object> setObjects(Message message) {
        Table table = message.get("table");
        TableToolData data = message.get("data");
        
        return setObject(table, data);
    }
    
    /**
     * 
     * @param objects
     */
    private final void setObjects(Context context) {
        if (context.data.objects == null || context.data.objects.length == 0) {
            switch(context.data.mode) {
            case TableTool.CONTINUOUS_DISPLAY:
            case TableTool.CONTINUOUS_UPDATE:
                context.table.clear();
                return;
            case TableTool.UPDATE:
            case TableTool.DISPLAY:
                additems(context, null);
                return;
            }
        }
        
        additems(context, context.data.objects);
    }
    
    /**
     * 
     * @param context
     * @param visible
     * @param columns
     */
    private final void setVisibility(
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
    public Button accept, add, remove;
    public View view;
}