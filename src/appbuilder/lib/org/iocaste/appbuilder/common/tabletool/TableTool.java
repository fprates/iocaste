package org.iocaste.appbuilder.common.tabletool;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.CustomContainer;
import org.iocaste.shell.common.ViewCustomAction;

public class TableTool {
    public static final String ADD = "add";
    public static final String REMOVE = "remove";
    public static final String ACCEPT = "accept";
    public static final byte CONTINUOUS_UPDATE = 0;
    public static final byte UPDATE = 1;
    public static final byte DISPLAY = 2;
    public static final byte DISABLED = 0;
    public static final byte ENABLED = 1;
    private TableToolData data;
    
    public TableTool(TableToolData data) {
        CustomContainer custom;
        Map<String, Map<String, Object>> columns = new HashMap<>();
        
        this.data = data;
        
        custom = new CustomContainer(data.container, data.name);
        custom.setRenderURL("/iocaste-appbuilder/services.html");
        custom.set("mark", false);
        custom.set("visible_lines", 15);
        custom.set("step", 1);
        custom.set("last", 0);
        custom.set("columns", columns);
        custom.set("enabled", true);
        custom.set("action", null);
        custom.set("controls_state", ENABLED);
        custom.set("controls", null);
        
        new Action(this, data, ACCEPT);
        new Action(this, data, ADD);
        new Action(this, data, REMOVE);
    }
    
    public final void accept() {
        getCustom().set("action", ACCEPT);
    }
    
    public final void add() {
        getCustom().set("action", ADD);
    }
    
    public final void controls(byte status, String... controls) {
        CustomContainer custom = getCustom();
        custom.set("controls_state", status);
        custom.set("controls", controls);
    }
    
    public final Map<String, Map<String, Object>> getColumns() {
        return getCustom().get("columns");
    }
    
    public final Container getContainer() {
        return getCustom();
    }
    
    private final CustomContainer getCustom() {
        return data.context.view.getElement(data.name);
    }
    
    public final ExtendedObject[] getObjects() {
        return getCustom().get("objects");
    }
    
    public final ExtendedObject[] getSelected() {
        return getCustom().get("selected");
    }
    
    public final void model(String model) {
        model(new Documents(data.context.function).getModel(model));
    }
    
    public final void model(DocumentModel model) {
        Map<String, Object> column;
        Map<String, Map<String, Object>> columns = getColumns();
        
        getCustom().set("model", model.getName());
        columns.clear();
        for (DocumentModelItem item : model.getItens()) {
            column = new HashMap<>();
            column.put("type", Const.TEXT_FIELD);
            column.put("disabled", false);
            column.put("visible", true);
            columns.put(item.getName(), column);
        }
    }
    
    public final void remove() {
        getCustom().set("action", REMOVE);
    }
    
    public final void setBorderStyle(String borderstyle) {
        getCustom().set("borderstyle", borderstyle);
    }
    
    public final void setColumnSize(String name, int size) {
        getColumns().get(name).put("size", size);
    }
    
    public final void setColumnStatus(byte status, String... tcolumns) {
        Map<String, Map<String, Object>> columns = getColumns();
        
        if (tcolumns == null || tcolumns.length == 0) {
            for (String cname :  columns.keySet())
                setEnabledColumn(cname, status == DISABLED);
        } else {
            for (String cname : tcolumns) {
                if (!columns.containsKey(cname))
                    throw new RuntimeException(cname.concat(
                            " is an invalid column."));
                setEnabledColumn(cname, status == DISABLED);
            }
        }
    }
    
    public final void setColumnType(String name, Const type, String action) {
        Map<String, Object> column = getColumns().get(name);
        column.put("type", type);
        column.put("action", action);
    }
    
    public final void setEnabledColumn(String name, boolean enabled) {
        getColumns().get(name).put("disabled", enabled);
    }
    
    public final void setItemColumn(String itemcolumn) {
        getCustom().set("itemcolumn", itemcolumn);
    }
    
    public final void setItemIncrement(String step) {
        getCustom().set("step", step);
    }
    
    public final void setMark(boolean mark) {
        getCustom().set("mark", mark);
    }
    
    public final void setMode(byte mode) {
        CustomContainer custom = getCustom();
        Map<String, Map<String, Object>> columns = custom.get("columns");
        
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
        
        custom.set("enabled", mode != DISPLAY);
        for (Map<String, Object> column : columns.values())
            column.put("disabled", mode == DISPLAY);
    }
    
    public final void setObjects(ExtendedObject[] objects) {
        getCustom().set("objects", objects);
    }
    
    public final void setValidator(String field, String validator) {
        getColumns().get(field).put("validator", validator);
    }
    
    public final void setVisibility(boolean visible, String... tcolumns) {
        Map<String, Object> properties;
        Map<String, Map<String, Object>> columns = getCustom().get("columns");
        
        for (Map<String, Object> column : columns.values())
            column.put("visible", !visible);
        
        for (String column : tcolumns) {
            properties = columns.get(column);
            if (properties == null)
                throw new RuntimeException(
                        column.concat(" is an invalid column."));
            properties.put("visible", visible);
        }
    }
    
    public final void setVisibleLines(int lines) {
        getCustom().set("visible_lines", lines);
    }
    
    public final int size() {
        ExtendedObject[] objects = getCustom().get("objects");
        return (objects == null)? 0 : objects.length;
    }
}

class Action implements ViewCustomAction {
    private static final long serialVersionUID = 7220679345842901434L;
    private String action;
    private TableTool tabletool;
    
    public Action(TableTool tabletool, TableToolData data, String action) {
        ((AbstractPage)data.context.function).register(
                action.concat(data.name), this);
        
        this.tabletool = tabletool;
        this.action = action;
    }
    
    @Override
    public void execute(AbstractContext context) throws Exception {
        switch (action) {
        case TableTool.ACCEPT:
            tabletool.accept();
            break;
        case TableTool.ADD:
            tabletool.add();
            break;
        case TableTool.REMOVE:
            tabletool.remove();
            break;
        }
    }
    
}