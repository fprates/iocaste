package org.iocaste.appbuilder.common.tabletool;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.GenericService;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.Validator;
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
    private static final String URL = "/iocaste-appbuilder/services.html";
    private AbstractContext context;
    private GenericService service;
    private String accept, add, remove;
    private TableToolData data;
    
    public TableTool(AbstractContext context, TableToolData data) {
        Map<String, Object> result;
        Container container, returned;
        Message message;
        
        message = new Message("render");
        message.add("data", data);
        message.add("view", context.view);
        
        service = new GenericService(context.function, URL);
        result = service.invoke(message);
        this.context = context;
        
        accept = new Action(this, data, ACCEPT).getName();
        add = new Action(this, data, ADD).getName();
        remove = new Action(this, data, REMOVE).getName();

        container = new StandardContainer(data.container, data.name);
        returned = (Container)result.get("container");
        this.data = (TableToolData)result.get("data");
        
        for (Element element : returned.getElements()) {
            transfer(container, element);
            element.setView(context.view);
            container.add(element);
        }
    }
    
    public final void accept() {
        Table table = getTable();
        
        context.view.getElement(accept).setVisible(false);
        context.view.getElement(add).setVisible(true);
        context.view.getElement(remove).setVisible(true);
        table.setTopLine(0);
    }
    
    public final void add() {
        int i = 0;
        Table table = getTable();
        
        switch (data.mode) {
        case CONTINUOUS_UPDATE:
            for (TableItem item_ : table.getItems()) {
                if (!item_.isSelected()) {
                    i++;
                    continue;
                }
                break;
            }
            
            additem(table, null, i);
            break;
        default:
            context.view.getElement(accept).setVisible(true);
            context.view.getElement(add).setVisible(false);
            context.view.getElement(remove).setVisible(false);
            additems(null);
            break;
        }
    }
    
    private final void additem(Table table, ExtendedObject object, int index) {
        
    }
    
    public final void additems() {
        additems(null);
    }
    
    private final void additems(ExtendedObject[] objects) {
        Message message = new Message("items_add");
        Table returned, table = getTable();
        
        data.objects = objects;
        message.add("table", table);
        message.add("data", data);
        returned = service.invoke(message);
        update(table, returned);
    }
    
    public final void clear() {
        getTable().clear();
        data.last = 0;
    }
    
    /**
     * 
     * @return
     */
    public final Container getContainer() {
        return context.view.getElement(data.name);
    }
    
    /**
     * 
     * @return
     */
    public final AbstractContext getContext() {
        return context;
    }
    
    /**
     * 
     * @return
     */
    public final Set<TableItem> getItems() {
        return getTable().getItems();
    }
    
    /**
     * 
     * @return
     */
    public final DocumentModel getModel() {
        return getTable().getModel();
    }
    
    /**
     * 
     * @return
     */
    public final ExtendedObject[] getObjects() {
        int i;
        ExtendedObject[] objects;
        Table table = getTable();
        Set<TableItem> items = table.getItems();
        int size = items.size();
        
        if (size == 0)
            return null;
        
        objects = new ExtendedObject[size];
        i = 0;
        for (TableItem item : items)
            objects[i++] = item.getObject();
        
        return objects;
    }
    
    public final List<ExtendedObject> getSelected() {
        return null;
    }
    
    /**
     * 
     * @return
     */
    private final Table getTable() {        
        return context.view.getElement(data.name.concat("_table"));
    }
    
    /**
     * 
     */
    public final void remove() {
        Table table = getTable();
        
        for (TableItem item : table.getItems())
            if (item.isSelected())
                table.remove(item);
    }
    
    /**
     * 
     * @param objects
     */
    public final void setObjects(ExtendedObject[] objects) {
        if (objects == null || objects.length == 0)
            additems(null);
        else
            additems(objects);
    }
    
    /**
     * 
     * @return
     */
    public final int size() {
        return getTable().size();
    }
    
    private void transfer(Container to, Element from) {
        Container fromc;
        
        if (!from.isContainable()) {
            from.setView(context.view);
            to.setView(context.view);
            context.view.index(from);
            return;
        }
        
        fromc = (Container)from;
        for (Element child : fromc.getElements())
            transfer(fromc, child);
        fromc.setView(context.view);
        context.view.index(from);
    }
    
    private void update(Table dest, Table source) {
        dest.clear();
        for (Element element : source.getElements())
            transfer(dest, element);
    }
}

class ValidatorData {
    public Class<? extends Validator> validator;
    public String[] inputs;
}

class Action implements ViewCustomAction {
    private static final long serialVersionUID = 7220679345842901434L;
    private String action, name;
    private TableTool tabletool;
    
    public Action(TableTool tabletool, TableToolData data, String action) {
        name = action.concat(data.name);
        ((AbstractPage)tabletool.getContext().function).register(name, this);
        
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
    
    public String getName() {
        return name;
    }
    
}