package org.iocaste.kernel.runtime.shell.tabletool;

import java.sql.Connection;
import java.util.Map;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.kernel.documents.GetDocumentModel;
import org.iocaste.protocol.IocasteException;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.StyleSheet;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.tooldata.ToolData;

public class TableRender extends AbstractTableHandler {

    private static final TableToolColumn columnInstance(
            TableContext context, ToolData data, DocumentModelItem item) {
        TableToolColumn ttcolumn = context.columns.get(data.name);
        
        if (ttcolumn != null)
            return ttcolumn;
        
        ttcolumn = new TableToolColumn();
        ttcolumn.tcolumn = new TableColumn(
                (Table)context.tabletool.getElement(), data.name);
        ttcolumn.tcolumn.setMark(false);
        ttcolumn.tcolumn.setVisible(!data.invisible);
        ttcolumn.tcolumn.setModelItem(item);
        ttcolumn.tcolumn.setLength(item.getDataElement().getLength());
        ttcolumn.tcolumn.setValueLocked(data.text != null);
        ttcolumn.tcolumn.setText(data.label);
        ttcolumn.data = data;
        context.columns.put(data.name, ttcolumn);
        return ttcolumn;
    }
    
    private static final DocumentModel getModel(TableContext context)
            throws Exception {
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
    
    /**
     * 
     * @param modelname
     */
    private static final void model(TableContext context) {
        String itemname;
        DocumentModelItem[] items;
        DocumentModelItem item;
        ToolData column;
        TableToolColumn ttcolumn;

        if (context.data.ordering == null) {
            items = context.model.getItens();
            context.data.ordering = new String[items.length];
            for (int i = 0; i < context.data.ordering.length; i++)
                context.data.ordering[i] = items[i].getName();
        }
        
        if (context.data.nsitem == null) {
            item = context.model.getNamespace();
            if (item != null) {
                itemname = item.getName();
                column = context.data.instance(itemname);
                ttcolumn = columnInstance(context, column, item);
                ttcolumn.tcolumn.setNamespace(true);
            }
        }
        
        for (String name : context.data.ordering) {
            column = context.data.get(name);
            if (column == null)
                column = context.data.instance(name);

            item = context.model.getModelItem(name);
            ttcolumn = columnInstance(context, column, item);
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
    
    public static final void run(TableContext context) throws Exception {
        Container container;
        Map<String, String> style;
        Table table;
        StyleSheet stylesheet;

        context.model = getModel(context);
        container = context.viewctx.view.getElement(context.data.parent);
        container.setStyleClass(context.data.style);
        
        stylesheet = StyleSheet.instance(context.viewctx.view);
        style = stylesheet.newElement(".tt_skip");
        style.put("border-style", "none");
        style.put("padding", "0.2em");
        style.put("margin", "0px");
        
        table = new Table(container, context.htmlname);
        table.setHeader(!context.data.getbl("noheader"));
        table.setStyleClass(
                Table.BORDER, context.data.getst("borderstyle"));
        table.setEnabled(!context.data.disabled);
        context.last = 0;
        context.tabletool.buildControls(table);
        model(context);
        setMode(context);
        setObjects(context);
    }

}
