package org.iocaste.appbuilder.common.tabletool;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.iocaste.appbuilder.common.AbstractComponentData;
import org.iocaste.appbuilder.common.AbstractComponentTool;
import org.iocaste.appbuilder.common.ComponentEntry;
import org.iocaste.appbuilder.common.ViewContext;
import org.iocaste.appbuilder.common.tabletool.actions.AcceptAction;
import org.iocaste.appbuilder.common.tabletool.actions.AddAction;
import org.iocaste.appbuilder.common.tabletool.actions.DeselectAllAction;
import org.iocaste.appbuilder.common.tabletool.actions.FirstAction;
import org.iocaste.appbuilder.common.tabletool.actions.LastAction;
import org.iocaste.appbuilder.common.tabletool.actions.NextAction;
import org.iocaste.appbuilder.common.tabletool.actions.PreviousAction;
import org.iocaste.appbuilder.common.tabletool.actions.RemoveAction;
import org.iocaste.appbuilder.common.tabletool.actions.SelectAllAction;
import org.iocaste.appbuilder.common.tabletool.actions.TableToolAction;
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
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.TableContextItem;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.Validator;

public class TableTool extends AbstractComponentTool {
    public static final byte CONTINUOUS_UPDATE = 0;
    public static final byte UPDATE = 1;
    public static final byte DISPLAY = 2;
    public static final byte CONTINUOUS_DISPLAY = 3;
    public static final byte DISABLED = 0;
    public static final byte ENABLED = 1;
    private Context extcontext;
    private DocumentModel model;
    private Map<String, TableToolAction> actionsstore;
    private Set<String> actions;
    
    /**
     * 
     * @param context
     * @param data
     */
    public TableTool(ComponentEntry entry) {
        super(entry);
        extcontext = new Context();
        actions = new LinkedHashSet<>();
    }
    
    /**
     * 
     */
    public final void accept() {
        Map<String, TableContextItem> ctxitems;
        
        ctxitems = getTable().getContextItems();
        ctxitems.get("accept").visible = false;
        ctxitems.get("add").visible = true;
        ctxitems.get("remove").visible = true;
        extcontext.data.topline = 0;
    }
    
    /**
     * 
     */
    public final void add() {
        Map<String, TableContextItem> ctxitems;
        
        switch (extcontext.data.mode) {
        case TableTool.CONTINUOUS_UPDATE:
            extcontext.data.vlines++;
            break;
        default:
            ctxitems = getTable().getContextItems();
            ctxitems.get("accept").visible = true;
            ctxitems.get("add").visible = false;
            ctxitems.get("remove").visible = false;
            break;
        }
        
        AddItem.run(this, extcontext.data);
        installValidators(extcontext);
    }
    
    /**
     * 
     */
    public final void additems() {
        additems(extcontext, null);
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
    
    public final void buildControls(Table table) {
        for (String name : actions)
            actionsstore.get(name).build(table);
    }
    
    /**
     * 
     */
    public final void clear() {
        getTable().clear();
        extcontext.data.last = 0;
    }
    
    public final void first() {
        Map<Integer, TableToolItem> items = extcontext.data.getItems();

        save(extcontext, items);
        extcontext.data.topline = 0;
        move(extcontext, items);
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
        
        for (String name : data.get().keySet()) {
            element = item.get(name);
            ttcolumn = data.get(name);
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
        return entry.data.context;
    }
    
    /**
     * 
     * @return
     */
    public final Set<TableItem> getItems() {
        return getTable().getItems();
    }
    
    private final Table getTable() {
        return (Table)getElement();
    }
    
    /**
     * 
     * @param data
     */
    private final void installValidators(Context extcontext) {
        InputComponent input;
        TableToolColumn column;
        Table table = getTable();
        ViewContext viewctx = entry.data.context.getView();
        
        for (String name : extcontext.data.get().keySet()) {
            column = extcontext.data.get(name);
            if (column.validator == null)
                continue;
            
            for (TableItem item : table.getItems()) {
                input = item.get(name);
                viewctx.validate(input, column.validator);
            }
        }
    }
    
    public final void last() {
        int pages;
        Map<Integer, TableToolItem> items = extcontext.data.getItems();
        
        save(extcontext, items);
        extcontext.data.topline = extcontext.data.vlines;
        pages = items.size() / extcontext.data.topline;
        extcontext.data.topline *= pages;
        move(extcontext, items);
    }
    
    @Override
    public final void load(AbstractComponentData componentdata) {
        Map<Integer, TableToolItem> ttitems;
        TableToolItem ttitem;
        int startline, i, ttitemssize, itemsdif;
        Set<TableItem> items;
        int itemssize;
        TableToolData data = (TableToolData)componentdata;
        
        items = getTable().getItems();
        itemssize = items.size();
        
        if (itemssize == 0)
            return;
        
        ttitems = data.getItems();
        if (data.vlines > 0) {
            startline = data.topline;
        } else {
            startline = 0;
        }
        
        ttitemssize = ttitems.size();
        itemsdif = itemssize - ttitemssize;
        if (itemsdif > 0)
            for (int j = 0; j < itemsdif; j++) {
                ttitem = new TableToolItem(data);
                ttitem.position += ttitems.size();
                ttitems.put(ttitem.position, ttitem);
            }
        i = startline;
        for (TableItem item : items) {
            ttitem = ttitems.get(i++);
            if (ttitem == null)
                break;
            ttitem.object = get(data, item);
            ttitem.selected = item.isSelected();
            ttitem.set(item);
        }
    }
    
    private final void move(
            Context context, Map<Integer, TableToolItem> ttitems) {
        TableToolItem ttitem;
        int l, lastline;
        Set<TableItem> items;
        TableColumn[] columns;
        Table table = getTable();
        
        items = table.getItems();
        l = context.data.vlines - items.size();
        if (l > 0)
            for (int i= 0; i < l; i++)
                items.add(TableRender.additem(this, context, null, -1));
        l = context.data.topline;
        lastline = ttitems.size() - 1;
        columns = table.getColumns();
        for (TableItem item : items) {
            if (l > lastline) {
                set(item, null);
                item.setVisible(false);
                continue;
            }

            ttitem = ttitems.get(l);
            ttitem.set(item);
            item.setVisible(true);
            set(item, ttitem.object);
            setLineProperties(context, columns, ttitem);
            l++;
        }
    }
    
    public final void next() {
        Map<Integer, TableToolItem> items = extcontext.data.getItems();
        int topline = extcontext.data.topline + extcontext.data.vlines;
        
        if (topline > items.size())
            return;
        save(extcontext, items);
        extcontext.data.topline = topline;
        move(extcontext, items);
    }
    
    public final void previous() {
        Map<Integer, TableToolItem> items = extcontext.data.getItems();
        int topline = extcontext.data.topline - extcontext.data.vlines;
        
        if (topline < 0)
            return;
        save(extcontext, items);
        extcontext.data.topline = topline;
        move(extcontext, items);
    }

    @Override
    public void refresh() {
        AbstractTableHandler.setObject(this, (TableToolData)entry.data);
    }
    
    /**
     * 
     */
    public final void remove() {
        int index;
        int i = 0;
        Map<Integer, TableToolItem> items = extcontext.data.getItems();
        Table table = getTable();
        
        for (TableItem item : table.getItems()) {
            if (!item.isSelected()) {
                i++;
                continue;
            }
            table.remove(item);
            index = i + extcontext.data.topline;
            if (items.size() > index)
                items.remove(index);
        }
        save(extcontext, items);
    }

    @Override
    public void run() {
        Map<String, TableContextItem> ctxitems;
        TableToolData data = (TableToolData)entry.data;

        if (actionsstore == null) {
            actionsstore = new LinkedHashMap<>();
            new SelectAllAction(this, data, actionsstore);
            new DeselectAllAction(this, data, actionsstore);
            new AcceptAction(this, data, actionsstore);
            new AddAction(this, data, actionsstore);
            new RemoveAction(this, data, actionsstore);
            new FirstAction(this, data, actionsstore);
            new PreviousAction(this, data, actionsstore);
            new NextAction(this, data, actionsstore);
            new LastAction(this, data, actionsstore);
        }
        
        actions.clear();
        if (data.actions != null) {
            for (String key : data.actions) {
                actions.add(key);
                if (!actionsstore.containsKey(key))
                    new CustomAction(this, data, actionsstore, key);
            }
        } else {
            actions.addAll(actionsstore.keySet());
        }
        
        extcontext.data = data;
        extcontext.htmlname = data.name.concat("_table");
        setHtmlName(extcontext.htmlname);
        TableRender.run(this, entry.data.context.function, extcontext);
        installValidators(extcontext);
        
        if (data.model != null)
            model = new Documents(
                    entry.data.context.function).getModel(data.model);
        else
            model = data.custommodel;

        ctxitems = getTable().getContextItems();
        for (String key : actions)
            if (actionsstore.get(key).isMarkable())
                ctxitems.get(key).visible = extcontext.data.mark;
    }

    private final void save(
            Context context, Map<Integer, TableToolItem> ttitems) {
        TableToolItem ttitem;
        int l = context.data.topline;
        Set<TableItem> items = getTable().getItems();
        
        for (TableItem item : items) {
            ttitem = ttitems.get(l);
            if (ttitem == null)
                break;
            ttitem.selected = item.isSelected();
            l++;
        }
    }
    
    public final void selectAll(boolean mark) {
        Map<Integer, TableToolItem> items = extcontext.data.getItems();
        
        for (TableItem item : getTable().getItems())
            item.setSelected(mark);
        for (TableToolItem item : items.values())
            item.selected = mark;
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
        SearchHelp.setTableItem(
                entry.data.context, getTable(), item, object);
    }
    
    public final void setLineProperties(
            Context context, TableColumn[] columns, TableToolItem ttitem) {
        String name;
        TableToolCell cell;
        TableItem item = ttitem.get();
        
        item.setSelected(ttitem.selected);
        if (ttitem.highlighted)
            item.setStyleClass(context.data.highlightstyle);
        else
            item.setStyleClass(null);
        for (TableColumn column : columns) {
            name = column.getName();
            cell = ttitem.getCell(name);
            if ((cell != null) && (cell.style != null))
                item.get(name).setStyleClass(cell.style);
        }
    }
    
    /**
     * 
     * @param objects
     */
    public final void setObjects(ExtendedObject[] objects) {
        extcontext.data.set(objects);
        AbstractTableHandler.setObject(this, extcontext.data);
        installValidators(extcontext);
    }
    
    public final void setVisibleNavigation(
            Context context, Map<Integer, TableToolItem> ttitems) {
        Map<String, TableContextItem> ctxitems;
        boolean visible;
        
        visible = ((ttitems.size() > context.data.vlines) &&
                (context.data.vlines > 0));
        
        ctxitems = getTable().getContextItems();
        for (String action : actions)
            if (actionsstore.get(action).isNavigable())
                ctxitems.get(action).visible = visible;
    }
}

class ValidatorData {
    public Class<? extends Validator> validator;
    public String[] inputs;
}
