package org.iocaste.appbuilder.common.tabletool;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.CustomComponent;
import org.iocaste.shell.common.TableItem;

public class TableTool {
    public static final String ADD = "add";
    public static final String REMOVE = "remove";
    public static final String ACCEPT = "accept";
    public static final byte CONTINUOUS_UPDATE = 0;
    public static final byte UPDATE = 1;
    public static final byte DISPLAY = 2;
    public static final byte DISABLED = 0;
    public static final byte ENABLED = 1;
    private CustomComponent custom;
    private Map<String, Map<String, Object>> columns;
    private TableToolData data;
    
    public TableTool(TableToolData data) {
        columns = new HashMap<>();
        this.data = data;
        
        custom = new CustomComponent(data.container, data.name);
        custom.setRenderURL("/iocaste-appbuilder/services.html");
        custom.set("mark", false);
        custom.set("visible_lines", 15);
        custom.set("step", 1);
        custom.set("columns", columns);
    }
    
    public final Container getContainer() {
        return custom.getContainer();
    }
    
    public final TableItem[] getItems() {
        return custom.get("items");
    }
    
    public final ExtendedObject[] getObjects() {
        return custom.get("objects");
    }
    
    public final void model(String model) {
        model(new Documents(data.context.function).getModel(model));
    }
    
    public final void model(DocumentModel model) {
        Map<String, Object> column;
        
        custom.set("model", model.getName());
        columns.clear();
        for (DocumentModelItem item : model.getItens()) {
            column = new HashMap<>();
            column.put("type", Const.TEXT_FIELD);
            column.put("disabled", false);
            columns.put(item.getName(), column);
        }
    }
    
    public final void setBorderStyle(String borderstyle) {
        custom.set("borderstyle", borderstyle);
    }
    
    public final void setItemColumn(String itemcolumn) {
        custom.set("itemcolumn", itemcolumn);
    }
    
    public final void setItemIncrement(String step) {
        custom.set("step", step);
    }
    
    public final void setMark(boolean mark) {
        custom.set("mark", mark);
    }
    
    public final void setMode(byte mode) {
        custom.set("mode", mode);
        switch (mode) {
        case CONTINUOUS_UPDATE:
        case UPDATE:
            custom.set("mark", true);
            break;
        case DISPLAY:
            custom.set("mark", false);
            break;
        }
    }
    
    public final void setObjects(ExtendedObject[] objects) {
        custom.set("objects", objects);
    }
    
    public final void setVisibility(boolean visible, String... columns) {
        Map<String, Object> properties;
        
        for (Map<String, Object> column : this.columns.values())
            column.put("visible", !visible);
        
        for (String column : columns) {
            properties = this.columns.get(column);
            if (properties == null)
                throw new RuntimeException(
                        column.concat(" is an invalid column."));
            properties.put("visible", visible);
        }
    }
    
    public final int size() {
        ExtendedObject[] objects = custom.get("objects");
        return (objects == null)? 0 : objects.length;
    }
}