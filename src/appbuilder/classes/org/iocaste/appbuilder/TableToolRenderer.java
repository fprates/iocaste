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
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.TextField;
import org.iocaste.shell.common.View;

public class TableToolRenderer extends AbstractFunction {
    private TableToolData data;
    private Table table;
    private String accept, add, remove;
    private View view;
    
    public TableToolRenderer() {
        export("add_action", "addaction");
        export("items_add", "addItems");
        export("objects_set", "setObjects");
        export("render", "render");
    }
    
    public final Map<String, Object> addaction(Message message) {
        Map<String, Object> result;
        int i = 0;
        
        table = message.get("table");
        data = message.get("data");
        
        switch (data.mode) {
        case TableTool.CONTINUOUS_UPDATE:
            for (TableItem item_ : table.getItems()) {
                if (!item_.isSelected()) {
                    i++;
                    continue;
                }
                break;
            }
            
            additem(null, i);
            break;
        default:
            additems(null);
            break;
        }
        
        result = new HashMap<>();
        result.put("table", table);
        result.put("data", data);
        return result;
    }
    
    private void additem(ExtendedObject object, int pos) {
        TableToolColumn column;
        Element element;
        DataElement delement;
        InputComponent input;
        String name;
        TableItem item = new TableItem(table, pos);
        TableColumn[] tcolumns = table.getColumns();
        
        for (TableColumn tcolumn : tcolumns) {
            if (tcolumn.isMark())
                continue;

            name = tcolumn.getName();
            column = data.columns.get(name);
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
            
            if (object == null && data.itemcolumn != null && name.
                    equals(data.itemcolumn)) {
                data.last += data.increment;
                if (element.isDataStorable()) {
                    input = (InputComponent)element;
                    input.set(data.last);
                } else {
                    ((Text)element).setText(Long.toString(data.last));
                }
            }
            
            if (column.disabled)
                element.setEnabled(false);
        }
        
        if (object == null)
            return;
        
        if (data.itemcolumn != null) {
            data.last += data.increment; 
            object.set(data.itemcolumn, data.last);
        }
        
        item.setObject(object);
    }
    
    private final void additems(ExtendedObject[] items) {
        int vlines = table.getVisibleLines();
        int total = table.size();
        
        if (items == null) {
            if (vlines == 0)
                vlines = 15;
            
            for (int i = 0; i < vlines; i++)
                additem(null, -1);
        } else {
            for (int i = 0; i < items.length; i++) {
                if ((vlines == i) && (vlines > 0))
                    break;
                
                additem(items[i], -1);
            }
        }
        
        table.setTopLine(total);
    }
    
    public final Map<String, Object> addItems(Message message) {
        Map<String, Object> result;
        
        table = message.get("table");
        data = message.get("data");
        additems(data.objects);
        
        result = new HashMap<>();
        result.put("table", table);
        result.put("data", data);
        return result;
    }
    
    /**
     * 
     * @param modelname
     */
    private final void model(String modelname) {
        DocumentModelItem modelitem;
        String name;
        TableToolColumn column;
        DocumentModel model = new Documents(this).getModel(modelname);
        
        if (model == null)
            throw new RuntimeException(modelname.
                    concat(" is an invalid model."));

        table.importModel(model);
        for (TableColumn tcolumn : table.getColumns()) {
            if (tcolumn.isMark())
                continue;
            
            name = tcolumn.getName();
            column = data.columns.get(name);
            if (column == null)
                column = new TableToolColumn(data, name);
            
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
        
        data = message.get("data");  
        view = message.get("view");
        container = new StandardContainer(data.container, data.name);
        
        accept = TableTool.ACCEPT.concat(data.name);
        new Button(container, accept);
        add = TableTool.ADD.concat(data.name);
        new Button(container, add);
        remove = TableTool.REMOVE.concat(data.name);
        new Button(container, remove);
        
        table = new Table(container, data.name.concat("_table"));
        table.setMark(true);
        table.setVisibleLines(15);
        data.last = 0;
        
        model(data.model);
        setMode(data.mode);
        setObjects(data.objects);
        result = new HashMap<>();
        result.put("container", container);
        result.put("data", data);
        return result;
    }
    
    /**
     * 
     * @param mode
     */
    private final void setMode(byte mode) {
        switch (mode) {
        case TableTool.CONTINUOUS_UPDATE:
        case TableTool.UPDATE:
            view.getElement(accept).setVisible(false);
            view.getElement(add).setVisible(true);
            view.getElement(remove).setVisible(true);
            break;
            
        case TableTool.DISPLAY:
            view.getElement(accept).setVisible(false);
            view.getElement(add).setVisible(false);
            view.getElement(remove).setVisible(false);
            table.setEnabled(false);
            for (String column : data.columns.keySet())
                data.columns.get(column).disabled = true;
            if (data.enableonly == null)
                break;
            
            for (String name : data.enableonly)
                if (!data.columns.containsKey(name))
                    throw new RuntimeException(
                            name.concat(" isn't a valid column."));
                else
                    data.columns.get(name).disabled = false;
            break;
        }

        table.setMark(data.mark);
        if (data.hide != null)
            setVisibility(false, data.hide);
        if (data.show != null)
            setVisibility(true, data.show);
    }
    
    public final Map<String, Object> setObjects(Message message) {
        Map<String, Object> result;
        
        table = message.get("table");
        table.clear();
        data = message.get("data");
        data.last = 0;
        
        setObjects(data.objects);
        
        result = new HashMap<>();
        result.put("table", table);
        result.put("data", data);
        return result;
    }
    
    /**
     * 
     * @param objects
     */
    private final void setObjects(ExtendedObject[] objects) {
        if (objects == null || objects.length == 0)
            additems(null);
        else
            additems(objects);
    }
    
    /**
     * 
     * @param visible
     * @param columns
     */
    private final void setVisibility(boolean visible, String... columns) {
        TableColumn tcolumn;
        
        for (TableColumn column : table.getColumns())
            if (!column.isMark())
                column.setVisible(!visible);
        
        for (String column : columns) {
            tcolumn = table.getColumn(column);
            if (tcolumn == null)
                throw new RuntimeException(
                        column.concat(" is an invalid column."));
            tcolumn.setVisible(visible);
        }
    }
}