package org.iocaste.kernel.runtime.shell.tabletool;

import java.sql.Connection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.kernel.documents.GetDocumentModel;
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
import org.iocaste.kernel.runtime.shell.tabletool.actions.AbstractTableToolAction;
import org.iocaste.protocol.IocasteException;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.CheckBox;
import org.iocaste.shell.common.Component;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.ListBox;
import org.iocaste.shell.common.SearchHelp;
import org.iocaste.shell.common.StyleSheet;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.TableContextItem;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.TextField;
import org.iocaste.shell.common.Validator;
import org.iocaste.shell.common.tooldata.ObjectMetaData;
import org.iocaste.shell.common.tooldata.ToolData;
import org.iocaste.shell.common.tooldata.ViewSpecItem.TYPES;

public class TableTool extends AbstractComponentTool {
    public static final byte CONTINUOUS_UPDATE = 0;
    public static final byte UPDATE = 1;
    public static final byte DISPLAY = 2;
    public static final byte CONTINUOUS_DISPLAY = 3;
    public static final byte DISABLED = 0;
    public static final byte ENABLED = 1;
    private TableContext context;
    private Map<String, AbstractTableToolAction> actionsstore;
    private Set<String> actions;
    
    /**
     * 
     * @param context
     * @param data
     */
    public TableTool(ViewContext viewctx, ComponentEntry entry) {
        super(viewctx, entry);
        context = new TableContext();
        context.viewctx = viewctx;
        context.data = entry.data;
        context.tabletool = this;
        actions = new LinkedHashSet<>();
        actionsstore = new LinkedHashMap<>();
        new SelectAllAction(context, actionsstore);
        new DeselectAllAction(context, actionsstore);
        new AcceptAction(context, actionsstore);
        new AddAction(context, actionsstore);
        new RemoveAction(context, actionsstore);
        new FirstAction(context, actionsstore);
        new PreviousAction(context, actionsstore);
        new NextAction(context, actionsstore);
        new LastAction(context, actionsstore);
    }
    
    private TableItem addLine(ToolData tooldata, int index, int pos) {
        ExtendedObject object;
        TableToolColumn ttcolumn;
        Element element;
        DataElement delement;
        InputComponent input;
        String name, paramlink, nsinput, itemcolumn;
        Link link;
        Button button;
        ObjectMetaData metadata;
        Table table = getElement();
        TableColumn[] tcolumns = table.getColumns();
        TableItem item = new TableItem(table, pos);

        if (tooldata == null) {
            object = null;
            metadata = null;
        } else {
            object = tooldata.objects.get(index);
            metadata = tooldata.metaobjects.get(index);
        }
        
        itemcolumn = context.data.indexitem;
        nsinput = null;
        for (TableColumn tcolumn : tcolumns) {
            if (tcolumn.isMark()) {
                ((InputComponent)item.get(tcolumn.getName())).set(
                        (metadata != null)? metadata.selected : false);
                continue;
            }
            
            name = tcolumn.getName();
            ttcolumn = context.columns.get(name);
            delement = tcolumn.getModelItem().getDataElement();
            input = null;
            switch (delement.getType()) {
            case DataType.BOOLEAN:
                element = new CheckBox(item, name);
                break;
            default:
                switch (ttcolumn.data.componenttype) {
                case TEXT:
                    element = new Text(item, name);
                    break;
                case LIST_BOX:
                    element = input = new ListBox(item, name);
                    input.setDataElement(delement);
                    if (ttcolumn.data.values == null)
                        break;
                    
                    for (String vname : ttcolumn.data.values.keySet())
                        ((ListBox)input).add(
                                vname, ttcolumn.data.values.get(vname));
                    break;
                case TEXT_FIELD:
                    element = input = new TextField(item, name);
                    input.setDataElement(delement);
                    break;
                case LINK:
                    element = link = new Link(
                            item, name, ttcolumn.data.actionname);
                    link.setText(null);
                    paramlink = new StringBuilder(context.data.name).
                            append(".").append(name).toString();
                    link.add(paramlink, null);
                    link.setNoScreenLock(ttcolumn.data.nolock);
                    break;
                case BUTTON:
                    context.viewctx.instance(TYPES.BUTTON, name);
                    element = button = new Button(context.viewctx, name);
                    button.setAction(ttcolumn.data.actionname);
                    button.setText(ttcolumn.data.text);
                    button.setNoScreenLock(ttcolumn.data.nolock);
                    break;
                default:
                    throw new RuntimeException("component type not supported"
                            + " in this version.");
                }
            }
            
            if ((object == null) && (itemcolumn != null) &&
                    name.equals(itemcolumn)) {
                context.last += context.data.step;
                if (element.isDataStorable()) {
                    input = (InputComponent)element;
                    input.set(context.last);
                } else {
                    ((Text)element).setText(Long.toString(context.last));
                }
            }
            
            element.setEnabled(!ttcolumn.data.disabled);
            if (tcolumn.isNamespace())
                nsinput = element.getHtmlName();
            
            if (input != null) {
                if (nsinput != null) {
                    input.setNSReference(nsinput);
                    continue;
                }
                
                if (ttcolumn.data.nsdata != null)
                    input.setNSReference(ttcolumn.data.nsdata);
            }
        }
        
        if (object == null) {
            set(item, null);
            return item;
        }
        
        if (itemcolumn != null) {
            context.last += context.data.step;
            object.set(itemcolumn, context.last);
        }
        
        set(item, object);
        return item;
    }
    
    private final void addLines() {
        int lastline;
        int vlines = context.data.vlength;
        
        if (context.data.objects.size() == 0) {
            if (vlines == 0)
                vlines = 15;
            
            for (int i = 0; i < vlines; i++)
                addLine(null, 0, -1);
        } else {
            if (vlines == 0)
                vlines = context.data.items.size();
            lastline = context.data.topline + vlines - 1;
            for (int key : context.data.objects.keySet()) {
                if (key < context.data.topline)
                    continue;
                if (key > lastline)
                    break;
                addLine(context.data, key, -1);
            }
        }
    }
    
    private final void addnsitem(DocumentModelItem nsitem) {
        ToolData column;
        TableToolColumn ttcolumn;
        String itemname = nsitem.getName();
        
        column = context.data.instance(itemname);
        ttcolumn = columnInstance(column, nsitem);
        ttcolumn.tcolumn.setNamespace(true);
        if (column.componenttype == null)
            column.componenttype = Const.TEXT_FIELD;
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
        context.last = 0;
    }

    private final TableToolColumn columnInstance(
            ToolData column, DocumentModelItem item) {
        TableToolColumn ttcolumn =
                context.columns.get(column.name);
        
        if (ttcolumn != null)
            return ttcolumn;
        
        ttcolumn = new TableToolColumn();
        ttcolumn.tcolumn = new TableColumn(getElement(), column.name);
        ttcolumn.tcolumn.setMark(false);
        ttcolumn.tcolumn.setVisible(!column.invisible);
        ttcolumn.tcolumn.setModelItem(item);
        ttcolumn.tcolumn.setLength(item.getDataElement().getLength());
        ttcolumn.tcolumn.setValueLocked(column.text != null);
        ttcolumn.tcolumn.setText(column.label);
        ttcolumn.data = column;
        context.columns.put(column.name, ttcolumn);
        return ttcolumn;
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
        ExtendedObject object = new ExtendedObject(context.model);
        
        for (String name : context.data.get().keySet()) {
            element = item.get(name);
            ttcolumn = context.columns.get(name);
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
    
    private final DocumentModel getModel() throws Exception {
        GetDocumentModel modelget;
        Connection connection;
        
        if (context.data.custommodel != null)
            return context.data.custommodel;
        
        if (context.data.model == null)
            throw new IocasteException(
                    "undefined model for %s.", context.data.name);
            
        modelget = context.viewctx.function.documents.
                get("get_document_model");
        connection = context.viewctx.function.documents.database.
                getDBConnection(context.viewctx.sessionid);
        return context.data.custommodel = modelget.run(connection,
                context.viewctx.function.documents, context.data.model);
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
//        for (String name : context.data.get().keySet()) {
//            column = context.data.instance(name);
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
    
    @Override
    public final void load() {
        int startline, finishline, j;
        TableItem[] items = getTable().getItems().toArray(new TableItem[0]);
        
        if (context.data.vlength > 0) {
            startline = context.data.topline;
            finishline = startline + context.data.vlength;
        } else {
            startline = 0;
            finishline = context.data.objects.size();
            entry.data.objects.clear();
        }
        
        j = 0;
        for (int i = startline; i < finishline; i++)
            entry.data.objects.put(
                    i, (j == items.length)? null : get(items[j++]));
    }
    
    /**
     * 
     * @param modelname
     */
    private final void model() {
        DocumentModelItem[] items;
        DocumentModelItem item;
        ToolData column;
        TableToolColumn ttcolumn;

        if (context.data.ordering == null) {
            items = context.model.getItens();
            if ((item = context.model.getNamespace()) == null) {
                context.data.ordering = new String[items.length];
                for (int i = 0; i < context.data.ordering.length; i++)
                    context.data.ordering[i] = items[i].getName();
            } else {
                context.data.ordering = new String[items.length + 1];
                context.data.ordering[0] = item.getName();
                for (int i = 1; i < context.data.ordering.length; i++)
                    context.data.ordering[i] = items[i - 1].getName();
                addnsitem(item);
            }
        }
        
        if (context.data.nsdata == null) {
            item = context.model.getNamespace();
            if (item != null)
                addnsitem(item);
        }
        
        for (String name : context.data.ordering) {
            column = context.data.instance(name);
            item = context.model.getModelItem(name);
            if (item == null)
                if (((item = context.model.getNamespace()) == null) ||
                        !item.getName().equals(name))
                    throw new IocasteException("invalid column %s.", name);
            
            ttcolumn = columnInstance(column, item);
            if (item.getSearchHelp() == null)
                item.setSearchHelp(column.sh);
            if (column.length > 0)
                ttcolumn.tcolumn.setLength(column.length);
            if (column.vlength > 0)
                ttcolumn.tcolumn.setVisibleLength(column.vlength);
            if (column.componenttype == null)
                column.componenttype = Const.TEXT_FIELD;
        }
    }

    @Override
    public void refresh() {
        setObject();
    }
    
    private final void render() throws Exception {
        Container container;
        Map<String, String> style;
        Table table;
        StyleSheet stylesheet;

        context.model = getModel();
        container = context.viewctx.view.getElement(context.data.parent);
        container.setStyleClass(context.data.style);
        
        stylesheet = StyleSheet.instance(context.viewctx.view);
        style = stylesheet.newElement(".tt_skip");
        style.put("border-style", "none");
        style.put("padding", "0.2em");
        style.put("margin", "0px");
        
        table = new Table(container, context.htmlname);
        table.setHeader(!context.data.noheader);
        table.setStyleClass(
                Table.BORDER, context.data.styles.get("borderstyle"));
        table.setEnabled(!context.data.disabled);
        context.last = 0;
        buildControls(table);
        model();
        setMode();
        setObjects();
    }

    @Override
    public void run() throws Exception {
        TableContextItem ctxitem;
        Map<String, TableContextItem> ctxitems;
        
        actions.clear();
        if (entry.data.actions.size() != 0) {
            for (String key : entry.data.actions) {
                actions.add(key);
                if (!actionsstore.containsKey(key))
                    new CustomAction(context, actionsstore, key);
            }
        } else {
            actions.addAll(actionsstore.keySet());
        }
        
        if (entry.data.vlength == 0)
            entry.data.vlength = 15;
        if (entry.data.step == 0)
            entry.data.step = 1;
        
        context.htmlname = entry.data.name.concat("_table");
        setHtmlName(context.htmlname);
        render();
        installValidators();

        ctxitems = getTable().getContextItems();
        for (String key : actions) {
            ctxitem = ctxitems.get(key);
            ctxitem.visible = context.data.mark;
            ctxitem.handler = actionsstore.get(key);
        }
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
            item.setStyleClass(context.data.styles.get("highlightstyle"));
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
     * @param mode
     */
    private final void setMode() {
        Table table = getElement();
        Map<String, TableContextItem> ctxitems;
        
        ctxitems = table.getContextItems();
        setVisible(ctxitems, "accept", false);
        setVisible(ctxitems, "add", !context.data.disabled);
        setVisible(ctxitems, "remove", !context.data.disabled);
        if (context.data.disabled) {
            table.setEnabled(false);
            for (String column : context.columns.keySet())
                context.columns.get(column).data.disabled = true;
        }

        table.setMark(context.data.mark);
    }

    private void setObject() {
        Table table = getElement();
        table.clear();
        context.last = 0;
        setObjects();
    }
    
    /**
     * 
     * @param objects
     */
    private final void setObjects() {
        Table table = getElement();
        
        setVisibleNavigation();
        if ((context.data.objects.size() == 0) && (context.data.vlength == 0))
            table.clear();
        
        addLines();
    }
    
    /**
     * 
     * @param objects
     */
    public final void setObjects(ExtendedObject[] objects) {
//        context.data.set(objects);
//        AbstractTableHandler.setObject(context);
//        installValidators(context);
    }
    
    private final void setVisible(Map<String, TableContextItem> ctxitems,
            String name, boolean visible) {
        if (ctxitems.containsKey(name))
            ctxitems.get(name).visible = visible;
    }
    
    private final void setVisibleNavigation() {
        Map<String, TableContextItem> ctxitems;
        boolean visible;
        
        visible = ((context.data.objects.size() > context.data.vlength)
                && (context.data.vlength > 0));
        
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
