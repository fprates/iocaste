package org.iocaste.appbuilder.common.tabletool;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.ViewContext;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.GenericService;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.Validator;
import org.iocaste.shell.common.View;
import org.iocaste.shell.common.ViewCustomAction;

public class TableTool {
    public static final String ADD = "add";
    public static final String REMOVE = "remove";
    public static final String ACCEPT = "accept";
    public static final byte CONTINUOUS_UPDATE = 0;
    public static final byte UPDATE = 1;
    public static final byte DISPLAY = 2;
    public static final byte CONTINUOUS_DISPLAY = 3;
    public static final byte DISABLED = 0;
    public static final byte ENABLED = 1;
    public static final String URL = "/iocaste-appbuilder/services.html";
    private AbstractContext context;
    private GenericService service;
    private String accept, add, remove, name;
    private TableToolData data;
    
    public TableTool(PageBuilderContext context, String name) {
        ViewContext viewcontext = context.getView();
        
        this.name = name;
        init(context, viewcontext.getComponents().getTableToolData(name));
    }
    
    public TableTool(AbstractContext context, TableToolData data) {
        init(context, data);
    }
    
    public final void accept() {
        Table table = getTable();
        
        context.view.getElement(accept).setVisible(false);
        context.view.getElement(add).setVisible(true);
        context.view.getElement(remove).setVisible(true);
        table.setTopLine(0);
    }
    
    public final void add() {
        if (getTableData().mode != TableTool.CONTINUOUS_UPDATE) {
            context.view.getElement(accept).setVisible(true);
            context.view.getElement(add).setVisible(false);
            context.view.getElement(remove).setVisible(false);
        }
        
        remote("add_action");
        installValidators();
    }
    
    public final void additems() {
        additems(null);
    }
    
    private final void additems(ExtendedObject[] objects) {
        getTableData().objects = objects;
        remote("item_add");
    }
    
    public final void clear() {
        getTable().clear();
        getTableData().last = 0;
    }
    
    public static final Table get(View view, TableToolData data) {
        return view.getElement(data.name.concat("_table"));
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
        List<ExtendedObject> objects;
        Table table = getTable();
        
        objects = new ArrayList<>();
        for (TableItem item : table.getItems())
            if (item.isSelected())
                objects.add(item.getObject());
        
        return (objects.size() == 0)? null : objects;
    }
    
    /**
     * 
     * @return
     */
    private final Table getTable() {        
        return context.view.getElement(getTableData().name.concat("_table"));
    }
    
    private final TableToolData getTableData() {
        ViewContext viewcontext; 
        
        if (name == null)
            return data;
        
        viewcontext = ((PageBuilderContext)context).getView();
        return viewcontext.getComponents().getTableToolData(name);
    }
    
    /**
     * 
     * @param context
     * @param data
     */
    private final void init(AbstractContext context, TableToolData data) {
        TableToolData rdata;
        Container container, returned;
        Message message;
        
        message = new Message("render");
        message.add("data", data);
        service = new GenericService(context.function, URL);
        rdata = service.invoke(message);
        
        this.context = context;
        accept = new Action(this, data, ACCEPT).getName();
        add = new Action(this, data, ADD).getName();
        remove = new Action(this, data, REMOVE).getName();

        returned = rdata.getContainer();
        setTableData(rdata);
        
        container = data.getContainer();
        for (Element element : returned.getElements()) {
            transfer(container, element);
            element.setView(context.view);
            container.add(element);
        }
        
        installValidators();
    }
    
    private final void installValidators() {
        InputComponent input;
        TableToolColumn column;
        TableToolData data = getTableData();
        Table table = getTable();
        
        for (String name : data.columns.keySet()) {
            column = data.columns.get(name);
            if (column.validator == null)
                continue;
            
            for (TableItem item : table.getItems()) {
                input = item.get(name);
                context.function.validate(input, column.validator); 
            }
        }
    }
    
    private final void remote(String function) {
        Table table;
        TableToolData data = getTableData();
        Message message = new Message(function);
        
        message.add("data", data);
        data = service.invoke(message);
        
        table = data.getContainer().getElement(data.name.concat("_table"));
        setTableData(data);
        update(table);
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
            getTableData().objects = null;
        else
            getTableData().objects = objects;
        
        remote("objects_set");
        installValidators();
    }
    
    public final void setTableData(TableToolData data) {
        PageBuilderContext context;
        
        if (name == null) {
            this.data = data;
        } else {
            context = (PageBuilderContext)this.context;
            context.getView().getComponents().set(data);
        }
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
    
    private void update(Element element) {
        Container container;
        
        if (element.isContainable()) {
            container = (Container)element;
            for (Element child : container.getElements())
                update(child);
        }
        
        element.setView(context.view);
        context.view.index(element);
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