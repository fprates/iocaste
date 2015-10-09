package org.iocaste.appbuilder.common.tabletool;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.ViewContext;
import org.iocaste.appbuilder.common.tabletool.actions.AcceptAction;
import org.iocaste.appbuilder.common.tabletool.actions.AddAction;
import org.iocaste.appbuilder.common.tabletool.actions.FirstAction;
import org.iocaste.appbuilder.common.tabletool.actions.LastAction;
import org.iocaste.appbuilder.common.tabletool.actions.NextAction;
import org.iocaste.appbuilder.common.tabletool.actions.PreviousAction;
import org.iocaste.appbuilder.common.tabletool.actions.RemoveAction;
import org.iocaste.appbuilder.common.tabletool.actions.TableToolAction;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
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
    public static final String FIRST = "first";
    public static final String LAST = "last";
    public static final byte CONTINUOUS_UPDATE = 0;
    public static final byte UPDATE = 1;
    public static final byte DISPLAY = 2;
    public static final byte CONTINUOUS_DISPLAY = 3;
    public static final byte DISABLED = 0;
    public static final byte ENABLED = 1;
    private AbstractContext context;
    private Context extcontext;
    private String name;
    private DocumentModel model;
    private Map<String, TableToolAction> actions;
    
    public TableTool(PageBuilderContext context, String name) {
        this(context, context.getView().getComponents().
                getTableToolData(name), name);
    }
    
    public TableTool(TableToolData data) {
        this(data.context, data, null);
    }
    
    /**
     * 
     * @param context
     * @param data
     */
    public TableTool(AbstractContext context, TableToolData data, String name) {
        this.context = context;
        this.name = name;
        extcontext = new Context();
        actions = new LinkedHashMap<>();
        
        if (data.actions != null)
            for (String action : data.actions)
                actions.put(action, new CustomAction(this, data, action));
        
        for (TableToolAction action : new TableToolAction[] {
                new AcceptAction(this, data),
                new AddAction(this, data),
                new RemoveAction(this, data),
                new FirstAction(this, data),
                new PreviousAction(this, data),
                new NextAction(this, data),
                new LastAction(this, data)
        })
            actions.put(action.getAction(), action);
        
        extcontext.data = data;
        
        setTableData(data);
        TableRender.run(this, context.function, extcontext);
        installValidators(extcontext);
        model = new Documents(context.function).getModel(data.model);
    }
    
    /**
     * 
     */
    public final void accept() {
        Context extcontext = getExtendedContext();
        getActionElement("accept").setVisible(false);
        getActionElement("add").setVisible(true);
        getActionElement("remove").setVisible(true);
        extcontext.data.topline = 0;
    }
    
    /**
     * 
     */
    public final void add() {
        Context extcontext = getExtendedContext();
        switch (extcontext.data.mode) {
        case TableTool.CONTINUOUS_UPDATE:
            extcontext.data.vlines++;
            break;
        default:
            getActionElement("accept").setVisible(true);
            getActionElement("add").setVisible(false);
            getActionElement("remove").setVisible(false);
            break;
        }
        
        AddItem.run(this, extcontext.data);
        installValidators(extcontext);
    }
    
    /**
     * 
     */
    public final void additems() {
        additems(getExtendedContext(), null);
    }
    
    /**
     * 
     * @param objects
     */
    private final void additems(Context extcontext, ExtendedObject[] objects) {
        extcontext.data.add(objects);
        AddItems.run(this, extcontext.data);
        installValidators(extcontext);
    }
    
    public final void buildControls(Container container) {
        for (String name : actions.keySet())
            actions.get(name).build(container);
    }
    
    /**
     * 
     */
    public final void clear() {
        Context context = getExtendedContext();
        context.table.clear();
        context.data.last = 0;
    }
    
    public final void first() {
        Context extcontext = getExtendedContext();
        List<TableToolItem> items = extcontext.data.getItems();
        
        extcontext.data.topline = 0;
        move(extcontext, items);
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
    
    public final Element getActionElement(String name) {
        return context.view.getElement(actions.get(name).getName());
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
    private final Context getExtendedContext() {
        ViewContext viewcontext; 

        extcontext.table = context.view.getElement(
                extcontext.data.name.concat("_table"));
        if (name == null)
            return extcontext;
        
        viewcontext = ((PageBuilderContext)context).getView();
        extcontext.data = viewcontext.getComponents().getTableToolData(name);
        return extcontext;
    }
    
    /**
     * 
     * @return
     */
    public final Set<TableItem> getItems() {
        Context extcontext = getExtendedContext();
        return extcontext.table.getItems();
    }
    
    /**
     * 
     * @param data
     */
    private final void installValidators(Context extcontext) {
        InputComponent input;
        TableToolColumn column;
        
        for (String name : extcontext.data.columns.keySet()) {
            column = extcontext.data.columns.get(name);
            if (column.validator == null)
                continue;
            
            for (TableItem item : extcontext.table.getItems()) {
                input = item.get(name);
                context.function.validate(input, column.validator); 
            }
        }
    }
    
    public final void last() {
        int pages;
        Context extcontext = getExtendedContext();
        List<TableToolItem> items = extcontext.data.getItems();
        
        extcontext.data.topline = extcontext.data.vlines;
        pages = items.size() / extcontext.data.topline;
        extcontext.data.topline *= pages;
        move(extcontext, items);
    }

    private final void move(Context context, List<TableToolItem> ttitems) {
        TableToolItem ttitem;
        int l, lastline;
        
        l = context.data.topline;
        lastline = ttitems.size() - 1;
        for (TableItem item : context.table.getItems()) {
            if (l > lastline) {
                set(item, null);
                continue;
            }
            
            ttitem = ttitems.get(l);
            ttitem.set(item);
            set(item, ttitem.object);
            l++;
        }
    }
    
    public final void next() {
        Context extcontext = getExtendedContext();
        List<TableToolItem> items = extcontext.data.getItems();
        int topline = extcontext.data.topline + extcontext.data.vlines;
        
        if (topline > items.size())
            return;
        extcontext.data.topline = topline;
        move(extcontext, items);
    }
    
    public final void previous() {
        Context extcontext = getExtendedContext();
        List<TableToolItem> items = extcontext.data.getItems();
        int topline = extcontext.data.topline - extcontext.data.vlines;
        
        if (topline < 0)
            return;
        extcontext.data.topline = topline;
        move(extcontext, items);
    }
    
    public final void refresh(TableToolData data) {
        Context extcontext = getExtendedContext();
        List<TableToolItem> ttitems;
        TableToolItem ttitem;
        int startline, endline, i, ttitemssize, itemsdif;
        Set<TableItem> items = extcontext.table.getItems();
        int itemssize = items.size();
        
        if (itemssize == 0)
            return;
        
        ttitems = data.getItems();
        if (data.vlines > 0) {
            startline = data.topline;
            endline = startline + data.vlines;
        } else {
            startline = endline = 0;
        }
        
        ttitemssize = ttitems.size();
        itemsdif = itemssize - ttitemssize;
        if (itemsdif > 0)
            for (int j = 0; j < itemsdif; j++)
                ttitems.add(new TableToolItem(data));

        i = -1;
        for (TableItem item : items) {
            i++;
            if (data.vlines > 0) {
                if (i < startline)
                    continue;
                if (i > endline)
                    break;
            }
            ttitem = ttitems.get(i);
            ttitem.object = get(data, item);
            ttitem.selected = item.isSelected();
            ttitem.set(item);
        }
    }
    
    /**
     * 
     */
    public final void remove() {
        Context extcontext = getExtendedContext();
        for (TableItem item : extcontext.table.getItems())
            if (item.isSelected())
                extcontext.table.remove(item);
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
        Context extcontext = getExtendedContext();
        SearchHelp.setTableItem(context, extcontext.table, item, object);
    }
    
    /**
     * 
     * @param objects
     */
    public final void setObjects(ExtendedObject[] objects) {
        Context extcontext = getExtendedContext();
        extcontext.data.set(objects);
        SetObjects.run(this, extcontext.data);
        installValidators(extcontext);
    }
    
    /**
     * 
     * @param data
     */
    public final void setTableData(TableToolData data) {
        PageBuilderContext context;
        Context extcontext = getExtendedContext();
        
        if (name == null) {
            extcontext.data = data;
        } else {
            context = (PageBuilderContext)this.context;
            context.getView().getComponents().set(data);
        }
    }
    
    public final void setVisibleNavigation(boolean visible) {
        for (String action : new String[] {
                TableTool.FIRST,
                TableTool.LAST,
                TableTool.PREVIOUS,
                TableTool.NEXT
        })
            getActionElement(action).setVisible(visible);
    }
    
    /**
     * 
     * @return
     */
    public final int size() {
        Context extcontext = getExtendedContext();
        return extcontext.table.size();
    }
}

class ValidatorData {
    public Class<? extends Validator> validator;
    public String[] inputs;
}
