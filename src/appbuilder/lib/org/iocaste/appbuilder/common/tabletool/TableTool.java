package org.iocaste.appbuilder.common.tabletool;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.ViewContext;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.SearchHelp;
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
    private AbstractContext context;
    private String accept, add, remove, name;
    private TableToolData data;
    private DocumentModel model;
    
    public TableTool(PageBuilderContext context, String name) {
        ViewContext viewcontext = context.getView();
        
        this.name = name;
        init(context, viewcontext.getComponents().getTableToolData(name));
    }
    
    public TableTool(TableToolData data) {
        init(data.context, data);
    }
    
    /**
     * 
     */
    public final void accept() {
        Table table = getTable();
        
        context.view.getElement(accept).setVisible(false);
        context.view.getElement(add).setVisible(true);
        context.view.getElement(remove).setVisible(true);
        table.setTopLine(0);
    }
    
    /**
     * 
     */
    public final void add() {
        TableToolData data = getTableData();
        switch (data.mode) {
        case TableTool.CONTINUOUS_UPDATE:
            data.vlines++;
            break;
        default:
            context.view.getElement(accept).setVisible(true);
            context.view.getElement(add).setVisible(false);
            context.view.getElement(remove).setVisible(false);
            break;
        }
        
        AddItem.run(this, data);
        installValidators(data);
    }
    
    /**
     * 
     */
    public final void additems() {
        additems(null);
    }
    
    /**
     * 
     * @param objects
     */
    private final void additems(ExtendedObject[] objects) {
        TableToolData data = getTableData();
        
        data.add(objects);
        AddItems.run(this, data);
        installValidators(data);
    }
    
    /**
     * 
     */
    public final void clear() {
        getTable().clear();
        getTableData().last = 0;
    }
    
    /**
     * 
     * @param view
     * @param data
     * @return
     */
    public static final Table get(View view, TableToolData data) {
        return view.getElement(data.name.concat("_table"));
    }
    
    /**
     * 
     * @param item
     * @return
     */
    public final ExtendedObject get(TableToolData data, TableItem item) {
        Element element;
        InputComponent input;
        DocumentModelItem modelitem;
        TableToolColumn ttcolumn;
        ExtendedObject object = new ExtendedObject(model);
        
        for (String name : data.columns.keySet()) {
            element = item.get(name);
            ttcolumn = data.columns.get(name);
            if (ttcolumn.tcolumn.isNamespace()) {
                input = (InputComponent)element;
                object.setNS(input.get());
                continue;
            }
            
            if (element.isDataStorable()) {
                input = (InputComponent)element;
                modelitem = input.getModelItem();
                
                if (modelitem == null)
                    continue;
                
                object.set(modelitem, input.get());
                continue;
            }
            
            if ((element.getType() == Const.LINK) && (model.contains(name)))
                object.set(name, ((Link)element).getText());
        }
        
        return object;
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
    public final List<TableToolItem> getItems(TableToolData data) {
        TableToolItem ttitem;
        List<TableToolItem> ttitems;
        Table table = getTable();
        Set<TableItem> items = table.getItems();
        int size = items.size();
        
        if (size == 0)
            return null;

        ttitems = new ArrayList<>();
        for (TableItem item : items) {
            ttitem = new TableToolItem();
            ttitem.object = get(data, item);
            ttitem.selected = item.isSelected();
            ttitem.item = item;
        }
        
        return ttitems;
    }
    
    /**
     * 
     * @return
     */
    private final Table getTable() {        
        return context.view.getElement(getTableData().name.concat("_table"));
    }
    
    /**
     * 
     * @return
     */
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
        this.context = context;
        accept = new Action(this, data, ACCEPT).getName();
        add = new Action(this, data, ADD).getName();
        remove = new Action(this, data, REMOVE).getName();

        setTableData(data);
        TableRender.run(this, context.function, data);
        installValidators(data);
        model = new Documents(context.function).getModel(data.model);
    }
    
    /**
     * 
     * @param data
     */
    private final void installValidators(TableToolData data) {
        InputComponent input;
        TableToolColumn column;
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
     * Importa objeto extendido.
     * 
     * Preenche componentes de entrada com valores do objeto extendido.
     * 
     * @param item
     * @param object
     */
    public final void set(TableItem item, ExtendedObject object) {
        SearchHelp.setTableItem(context, getTable(), item, object);
    }
    
    /**
     * 
     * @param objects
     */
    public final void setObjects(ExtendedObject[] objects) {
        TableToolData data = getTableData();
        
        data.set(objects);
        SetObjects.run(this, data);
        installValidators(data);
    }
    
    /**
     * 
     * @param data
     */
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