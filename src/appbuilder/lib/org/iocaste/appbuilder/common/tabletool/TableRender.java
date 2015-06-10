package org.iocaste.appbuilder.common.tabletool;

import java.util.Map;

import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolColumn;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.StyleSheet;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;

public class TableRender extends AbstractTableHandler {
    
    /**
     * 
     * @param modelname
     */
    private static final void model(Function function, Context context) {
        String itemname;
        DocumentModelItem[] items;
        DocumentModelItem item;
        TableToolColumn column;
        DocumentModel model = new Documents(function).
                getModel(context.data.model);
        
        if (model == null)
            throw new RuntimeException(context.data.model.
                    concat(" is an invalid model."));

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
                column = new TableToolColumn(context.data, itemname);
                setTableToolColumn(context, column, itemname, item);
                column.tcolumn.setNamespace(true);
            }
        }
        
        for (String name : context.data.ordering) {
            column = context.data.columns.get(name);
            if (column == null)
                column = new TableToolColumn(context.data, name);
            item = model.getModelItem(name);
            
            setTableToolColumn(context, column, name, item);
            
            if (item.getSearchHelp() == null)
                item.setSearchHelp(column.sh);
            
            if (column.size > 0)
                column.tcolumn.setLength(column.size);
        }
    }
    
    public static final void run(TableTool tabletool, Function function,
            TableToolData data) {
        StyleSheet stylesheet;
        Container container, supercontainer;
        Map<String, String> style;
        Context context = new Context();
        
        context.data = data;
        if (data.container != null) {
            supercontainer = data.context.view.getElement(data.container);
            container = new StandardContainer(supercontainer, data.name);
        } else {
            container = data.context.view.getElement(data.name);
        }
        
        stylesheet = data.context.view.styleSheetInstance();
        style = stylesheet.newElement(".tt_skip");
        style.put("border-style", "none");
        style.put("padding", "0.2em");
        style.put("margin", "0px");
        
        context.accept = new Button(
                container, TableTool.ACCEPT.concat(context.data.name));
        context.add = new Button(
                container, TableTool.ADD.concat(context.data.name));
        context.remove = new Button(container,
                TableTool.REMOVE.concat(context.data.name));
        
        new StandardContainer(container, context.data.name.concat("_skip")).
                setStyleClass("tt_skip");
        
        context.table = new Table(
                container, context.data.name.concat("_table"));
        context.table.setVisibleLines(context.data.vlines);
        context.table.setHeader(!context.data.noheader);
        context.data.last = 0;
        
        model(function, context);
        setMode(context);
        setObjects(tabletool, context);
    }

    private static final void setTableToolColumn(Context context,
            TableToolColumn column, String name, DocumentModelItem item) {
        
        column.tcolumn = new TableColumn(context.table, name);
        column.tcolumn.setMark(false);
        column.tcolumn.setVisible(true);
        column.tcolumn.setModelItem(item);
        column.tcolumn.setLength(item.getDataElement().getLength());
    }

}
