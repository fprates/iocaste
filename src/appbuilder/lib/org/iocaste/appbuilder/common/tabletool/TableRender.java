package org.iocaste.appbuilder.common.tabletool;

import java.util.Map;

import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolColumn;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.StyleSheet;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;

public class TableRender extends AbstractTableHandler {
    
    /**
     * 
     * @param modelname
     */
    private static final void model(
            TableTool tabletool, Function function, Context context) {
        String itemname;
        DocumentModelItem[] items;
        DocumentModelItem item;
        TableToolColumn column;
        DocumentModel model = null;
        
        if (context.data.model != null)
            model = new Documents(function).getModel(context.data.model);
        
        if (context.data.refmodel != null)
            model = context.data.refmodel;
        
        if (model == null)
            throw new RuntimeException("model not defined.");

        if (context.data.ordering == null) {
            items = model.getItens();
            context.data.ordering = new String[items.length];
            for (int i = 0; i < context.data.ordering.length; i++)
                context.data.ordering[i] = items[i].getName();
        }
        
        if (context.data.nsfield == null) {
            item = model.getNamespace();
            if (item != null) {
                itemname = item.getName();
                column = context.data.instance(itemname);
                setTableToolColumn(tabletool, column, itemname, item);
                column.tcolumn.setNamespace(true);
            }
        }
        
        for (String name : context.data.ordering) {
            column = context.data.get(name);
            if (column == null)
                column = context.data.instance(name);
            item = model.getModelItem(name);
            
            setTableToolColumn(tabletool, column, name, item);
            
            if (item.getSearchHelp() == null)
                item.setSearchHelp(column.sh);
            
            if (column.length > 0)
                column.tcolumn.setLength(column.length);
            if (column.vlength > 0)
                column.tcolumn.setVisibleLength(column.vlength);
        }
    }
    
    public static final void run(TableTool tabletool, Function function,
            Context context) {
        StyleSheet stylesheet;
        Container container;
        Map<String, String> style;
        Table table;
        
        container = context.data.context.view.getElement(context.data.name);
        container.setStyleClass(context.data.style);
        
        stylesheet = context.data.context.view.styleSheetInstance();
        style = stylesheet.newElement(".tt_skip");
        style.put("border-style", "none");
        style.put("padding", "0.2em");
        style.put("margin", "0px");
        
        table = new Table(container, context.htmlname);
        table.setHeader(!context.data.noheader);
        table.setBorderStyle(context.data.borderstyle);
        context.data.last = 0;

        tabletool.buildControls(table);
        model(tabletool, function, context);
        setMode(tabletool, context);
        setObjects(tabletool, context);
    }

    private static final void setTableToolColumn(TableTool tabletool,
            TableToolColumn column, String name, DocumentModelItem item) {
        
        column.tcolumn = new TableColumn((Table)tabletool.getElement(), name);
        column.tcolumn.setMark(false);
        column.tcolumn.setVisible(true);
        column.tcolumn.setModelItem(item);
        column.tcolumn.setLength(item.getDataElement().getLength());
        column.tcolumn.setValueLocked(column.text != null);
        column.tcolumn.setText(column.label);
    }

}
