package org.iocaste.appbuilder.common.tabletool;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.iocaste.appbuilder.common.AbstractComponentData;
import org.iocaste.appbuilder.common.AbstractComponentTool;
import org.iocaste.appbuilder.common.ComponentEntry;
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
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.SearchHelp;
import org.iocaste.shell.common.TableColumn;
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
    private Map<String, TableToolAction> actions;
    
    /**
     * 
     * @param context
     * @param data
     */
    public TableTool(ComponentEntry entry) {
        super(entry);
        extcontext = new Context();
        actions = new LinkedHashMap<>();
    }
    
    /**
     * 
     */
    public final void accept() {
        getActionElement("accept").setVisible(false);
        getActionElement("add").setVisible(true);
        getActionElement("remove").setVisible(true);
        extcontext.data.topline = 0;
    }
    
    /**
     * 
     */
    public final void add() {
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
    
    public final void buildControls(Container container) {
        for (String name : actions.keySet())
            actions.get(name).build(container);
    }
    
    /**
     * 
     */
    public final void clear() {
        extcontext.table.clear();
        extcontext.data.last = 0;
    }
    
    public final void first() {
        List<TableToolItem> items = extcontext.data.getItems();

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
        return entry.data.context.view.getElement(actions.get(name).getName());
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
                entry.data.context.function.validate(input, column.validator);
            }
        }
    }
    
    public final void last() {
        int pages;
        List<TableToolItem> items = extcontext.data.getItems();
        
        save(extcontext, items);
        extcontext.data.topline = extcontext.data.vlines;
        pages = items.size() / extcontext.data.topline;
        extcontext.data.topline *= pages;
        move(extcontext, items);
    }
    
    @Override
    public final void load(AbstractComponentData componentdata) {
        TableToolData data = (TableToolData)componentdata;
        List<TableToolItem> ttitems;
        TableToolItem ttitem;
        int startline, i, ttitemssize, itemsdif;
        Set<TableItem> items = extcontext.table.getItems();
        int itemssize = items.size();
        
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
            for (int j = 0; j < itemsdif; j++)
                ttitems.add(new TableToolItem(data));

        i = startline;
        for (TableItem item : items) {
            try {
                ttitem = ttitems.get(i++);
            } catch (IndexOutOfBoundsException e) {
                break;
            }
            ttitem.object = get(data, item);
            ttitem.selected = item.isSelected();
            ttitem.set(item);
        }
    }
    
    private final void move(Context context, List<TableToolItem> ttitems) {
        TableToolItem ttitem;
        int l, lastline;
        Set<TableItem> items;
        TableColumn[] columns;
        
        items = context.table.getItems();
        l = context.data.vlines - items.size();
        if (l > 0)
            for (int i= 0; i < l; i++)
                items.add(TableRender.additem(this, context, null, -1));
        l = context.data.topline;
        lastline = ttitems.size() - 1;
        columns = context.table.getColumns();
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
        List<TableToolItem> items = extcontext.data.getItems();
        int topline = extcontext.data.topline + extcontext.data.vlines;
        
        if (topline > items.size())
            return;
        save(extcontext, items);
        extcontext.data.topline = topline;
        move(extcontext, items);
    }
    
    public final void previous() {
        List<TableToolItem> items = extcontext.data.getItems();
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
        int i = 0;
        List<TableToolItem> items = extcontext.data.getItems();
        for (TableItem item : extcontext.table.getItems()) {
            if (!item.isSelected()) {
                i++;
                continue;
            }
            extcontext.table.remove(item);
            items.remove(i + extcontext.data.topline);
        }
        save(extcontext, items);
    }

    @Override
    public void run() {
        TableToolData data = (TableToolData)entry.data;
        
        if (data.actions != null)
            for (String action : data.actions)
                actions.put(action, new CustomAction(this, data, action));
        
        for (TableToolAction action : new TableToolAction[] {
                new SelectAllAction(this, data),
                new DeselectAllAction(this, data),
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
        extcontext.htmlname = data.name.concat("_table");
        setHtmlName(extcontext.htmlname);
        TableRender.run(this, entry.data.context.function, extcontext);
        installValidators(extcontext);
        
        if (data.model != null)
            model = new Documents(
                    entry.data.context.function).getModel(data.model);
        else
            model = data.refmodel;
        
        for (String key : actions.keySet())
            if (actions.get(key).isMarkable())
                getActionElement(key).setVisible(extcontext.data.mark);
    }

    private final void save(Context context, List<TableToolItem> ttitems) {
        TableToolItem ttitem;
        int l = context.data.topline;
        Set<TableItem> items = context.table.getItems();
        
        for (TableItem item : items) {
            try {
                ttitem = ttitems.get(l);
            } catch (IndexOutOfBoundsException e) {
                break;
            }
            ttitem.selected = item.isSelected();
            l++;
        }
    }
    
    public final void selectAll(boolean mark) {
        List<TableToolItem> items = extcontext.data.getItems();
        
        for (TableItem item : extcontext.table.getItems())
            item.setSelected(mark);
        for (TableToolItem item : items)
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
                entry.data.context, extcontext.table, item, object);
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
            Context context, List<TableToolItem> ttitems) {
        boolean visible;
        visible = ((ttitems.size() > context.data.vlines) &&
                (context.data.vlines > 0));
        for (String action : actions.keySet())
            if (actions.get(action).isNavigable())
                getActionElement(action).setVisible(visible);
    }
    
    /**
     * 
     * @return
     */
    public final int size() {
        return extcontext.table.size();
    }
}

class ValidatorData {
    public Class<? extends Validator> validator;
    public String[] inputs;
}
