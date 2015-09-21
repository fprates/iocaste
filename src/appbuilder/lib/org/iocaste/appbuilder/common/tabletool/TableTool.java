package org.iocaste.appbuilder.common.tabletool;

import java.util.List;
import java.util.Set;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.ViewContext;
import org.iocaste.appbuilder.common.tabletool.actions.AcceptAction;
import org.iocaste.appbuilder.common.tabletool.actions.AddAction;
import org.iocaste.appbuilder.common.tabletool.actions.NextAction;
import org.iocaste.appbuilder.common.tabletool.actions.PreviousAction;
import org.iocaste.appbuilder.common.tabletool.actions.RemoveAction;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.SearchHelp;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.Validator;
import org.iocaste.shell.common.View;

public class TableTool {
    public static final String ADD = "add";
    public static final String REMOVE = "remove";
    public static final String ACCEPT = "accept";
    public static final String PREVIOUS = "previous";
    public static final String NEXT = "next";
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
        accept = new AcceptAction(this, data).getName();
        add = new AddAction(this, data).getName();
        remove = new RemoveAction(this, data).getName();
        new PreviousAction(this, data);
        new NextAction(this, data);
        
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
    
    public final void next() {
        Table table = getTable();
        int topline = table.getTopLine() + getTableData().vlines;
        if (topline > table.size())
            return;
        getTable().setTopLine(topline);
    }
    
    public final void previous() {
        Table table = getTable();
        int topline = table.getTopLine() - getTableData().vlines;
        if (topline < 0)
            return;
        getTable().setTopLine(topline);
    }
    
    public final void refresh(TableToolData data) {
        List<TableToolItem> ttitems;
        TableToolItem ttitem;
        Table table = getTable();
        Set<TableItem> items = table.getItems();
        int size = items.size();
        
        ttitems = data.getItems();
        ttitems.clear();
        
        if (size == 0)
            return;

        for (TableItem item : items) {
            ttitem = new TableToolItem();
            ttitem.object = get(data, item);
            ttitem.selected = item.isSelected();
            ttitem.item = item;
            ttitems.add(ttitem);
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
