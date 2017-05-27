package org.iocaste.kernel.runtime.shell.tabletool;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.kernel.runtime.shell.AbstractComponentTool;
import org.iocaste.kernel.runtime.shell.ComponentEntry;
import org.iocaste.kernel.runtime.shell.ViewContext;
import org.iocaste.kernel.runtime.shell.tabletool.actions.AcceptAction;
import org.iocaste.kernel.runtime.shell.tabletool.actions.AddAction;
import org.iocaste.kernel.runtime.shell.tabletool.actions.DeselectAllAction;
import org.iocaste.kernel.runtime.shell.tabletool.actions.FirstAction;
import org.iocaste.kernel.runtime.shell.tabletool.actions.LastAction;
import org.iocaste.kernel.runtime.shell.tabletool.actions.NextAction;
import org.iocaste.kernel.runtime.shell.tabletool.actions.PreviousAction;
import org.iocaste.kernel.runtime.shell.tabletool.actions.RemoveAction;
import org.iocaste.kernel.runtime.shell.tabletool.actions.SelectAllAction;
import org.iocaste.kernel.runtime.shell.tabletool.actions.TableToolAction;
import org.iocaste.shell.common.Component;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;
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
    private TableContext extcontext;
    private Map<String, TableToolAction> actionsstore;
    private Set<String> actions;
    
    /**
     * 
     * @param context
     * @param data
     */
    public TableTool(ViewContext viewctx, ComponentEntry entry) {
        super(viewctx, entry);
        extcontext = new TableContext();
        extcontext.viewctx = viewctx;
        extcontext.tabletool = this;
        extcontext.data = entry.data;
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
        extcontext.data.set("topline", 0);
    }
    
    /**
     * 
     */
    public final void add() {
        Map<String, TableContextItem> ctxitems;
        
        switch (extcontext.data.mode) {
        case TableTool.CONTINUOUS_UPDATE:
            extcontext.data.vlength++;
            break;
        default:
            ctxitems = getTable().getContextItems();
            ctxitems.get("accept").visible = true;
            ctxitems.get("add").visible = false;
            ctxitems.get("remove").visible = false;
            break;
        }
        
        AddItem.run(this, extcontext.data);
        installValidators();
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
//        extcontext.data.add(objects);
//        AddItems.run(this, extcontext.data);
//        installValidators(extcontext);
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
        extcontext.last = 0;
    }
    
    public final void first() {
        save();
        extcontext.data.set("topline", 0);
        move();
    }
    
    /**
     * 
     * @param item
     * @return
     */
    public final ExtendedObject get(TableItem item) {
        Element element;
        InputComponent input;
        DocumentModelItem modelitem;
        TableToolColumn ttcolumn;
        ExtendedObject object = new ExtendedObject(extcontext.model);
        
        for (String name : extcontext.data.get().keySet()) {
            element = item.get(name);
            ttcolumn = extcontext.columns.get(name);
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
            
            if (ttcolumn.data.actionname == null)
                continue;
            object.set(name, ((Component)element).getText());
        }
        
        return object;
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
    private final void installValidators() {
//        InputComponent input;
//        ToolData column;
//        Table table = getTable();
//        
//        for (String name : extcontext.data.get().keySet()) {
//            column = extcontext.data.instance(name);
//            if (column.validators.size() == 0)
//                continue;
//            
//            for (TableItem item : table.getItems()) {
//                input = item.get(name);
//                for (String validator : column.validators)
//                    viewctx.validate(input, validator);
//            }
//        }
    }
    
    public final void last() {
        int pages, topline;
        save();
        pages = extcontext.items.size() / (topline = extcontext.data.vlength);
        extcontext.data.set("topline", (topline *= pages));
        move();
    }
    
    @Override
    public final void load() {
        TableToolItem ttitem;
        int startline, i, ttitemssize, itemsdif;
        Set<TableItem> items;
        int itemssize;
        
        items = getTable().getItems();
        itemssize = items.size();
        
        if (itemssize == 0)
            return;
        
        startline = (extcontext.data.vlength > 0)?
                extcontext.data.geti("topline") : 0;
        ttitemssize = extcontext.items.size();
        itemsdif = itemssize - ttitemssize;
        if (itemsdif > 0)
            for (int j = 0; j < itemsdif; j++) {
                ttitem = new TableToolItem(extcontext);
                ttitem.position += extcontext.items.size();
                extcontext.items.put(ttitem.position, ttitem);
            }
        i = startline;
        for (TableItem item : items) {
            ttitem = extcontext.items.get(i++);
            if (ttitem == null)
                break;
            ttitem.object = get(item);
            ttitem.selected = item.isSelected();
            ttitem.set(item);
        }
    }
    
    private final void move() {
        TableToolItem ttitem;
        int l, lastline;
        Set<TableItem> items;
        TableColumn[] columns;
        Table table = getTable();
        
        items = table.getItems();
        l = extcontext.data.vlength - items.size();
        if (l > 0)
            for (int i= 0; i < l; i++)
                items.add(TableRender.additem(extcontext, null, -1));
        l = extcontext.data.geti("topline");
        lastline = extcontext.items.size() - 1;
        columns = table.getColumns();
        for (TableItem item : items) {
            if (l > lastline) {
                set(item, null);
                item.setVisible(false);
                continue;
            }

            ttitem = extcontext.items.get(l);
            ttitem.set(item);
            item.setVisible(true);
            set(item, ttitem.object);
            setLineProperties(columns, ttitem);
            l++;
        }
    }
    
    public final void next() {
        int topline = extcontext.data.geti("topline") + extcontext.data.vlength;
        if (topline > extcontext.items.size())
            return;
        save();
        extcontext.data.set("topline", topline);
        move();
    }
    
    public final void previous() {
        int topline = extcontext.data.geti("topline") - extcontext.data.vlength;
        if (topline < 0)
            return;
        save();
        extcontext.data.set("topline", topline);
        move();
    }

    @Override
    public void refresh() {
        AbstractTableHandler.setObject(extcontext);
    }
    
    /**
     * 
     */
    public final void remove() {
        Table table;
        int index;
        
        entry.component.load();
        table = getTable();
        index = extcontext.data.geti("topline");
        
        for (TableItem item : table.getItems()) {
            if (!item.isSelected()) {
                index++;
                continue;
            }
            table.remove(item);
            extcontext.items.remove(index++);
        }
        save();
    }

    @Override
    public void run() throws Exception {
        Map<String, TableContextItem> ctxitems;
        
        if (actionsstore == null) {
            actionsstore = new LinkedHashMap<>();
            new SelectAllAction(extcontext, actionsstore);
            new DeselectAllAction(extcontext, actionsstore);
            new AcceptAction(extcontext, actionsstore);
            new AddAction(extcontext, actionsstore);
            new RemoveAction(extcontext, actionsstore);
            new FirstAction(extcontext, actionsstore);
            new PreviousAction(extcontext, actionsstore);
            new NextAction(extcontext, actionsstore);
            new LastAction(extcontext, actionsstore);
        }
        
        actions.clear();
        if (entry.data.actions != null) {
            for (String key : entry.data.actions) {
                actions.add(key);
                if (!actionsstore.containsKey(key))
                    new CustomAction(extcontext, actionsstore, key);
            }
        } else {
            actions.addAll(actionsstore.keySet());
        }
        
        if (entry.data.vlength == 0)
            entry.data.vlength = 15;
        if (entry.data.geti("step") == 0)
            entry.data.set("step", 1);
        if (entry.data.geti("increment") == 0)
            entry.data.set("increment", 1);
        
        extcontext.htmlname = entry.data.name.concat("_table");
        setHtmlName(extcontext.htmlname);
        TableRender.run(extcontext);
        installValidators();

        ctxitems = getTable().getContextItems();
        for (String key : actions)
            if (actionsstore.get(key).isMarkable())
                ctxitems.get(key).visible = extcontext.data.getbl("mark");
    }

    private final void save() {
        TableToolItem ttitem;
        int l = extcontext.data.geti("topline");
        Set<TableItem> items = getTable().getItems();
        
        for (TableItem item : items) {
            ttitem = extcontext.items.get(l);
            if (ttitem == null)
                break;
            ttitem.selected = item.isSelected();
            l++;
        }
    }
    
    public final void selectAll(boolean mark) {
        entry.component.load();
        for (TableItem item : getTable().getItems())
            item.setSelected(mark);
        save();
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
        SearchHelp.setTableItem(getTable(), item, object);
    }
    
    public final void setLineProperties(
            TableColumn[] columns, TableToolItem ttitem) {
        String name;
        TableToolCell cell;
        TableItem item = ttitem.get();
        
        item.setSelected(ttitem.selected);
        if (ttitem.highlighted)
            item.setStyleClass(extcontext.data.getst("highlightstyle"));
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
//        extcontext.data.set(objects);
//        AbstractTableHandler.setObject(extcontext);
//        installValidators(extcontext);
    }
    
    public final void setVisibleNavigation() {
        Map<String, TableContextItem> ctxitems;
        boolean visible;
        
        visible = ((extcontext.items.size() > extcontext.data.vlength) &&
                (extcontext.data.vlength > 0));
        
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
