package org.iocaste.appbuilder.common.tabletool;

import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolColumn;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;

public class TableRender extends AbstractTableHandler {

    public static final void run(Function function, TableToolData data) {
        Container container, supercontainer;
        Context context = new Context();
        
        context.data = data;
        if (data.container != null) {
            supercontainer = data.context.view.getElement(data.container);
            container = new StandardContainer(supercontainer, data.name);
        } else {
            container = data.context.view.getElement(data.name);
        }
        
        context.accept = new Button(
                container, TableTool.ACCEPT.concat(context.data.name));
        context.add = new Button(
                container, TableTool.ADD.concat(context.data.name));
        context.remove = new Button(container,
                TableTool.REMOVE.concat(context.data.name));
        
        context.table = new Table(
                container, context.data.name.concat("_table"));
        context.table.setVisibleLines(context.data.vlines);
        context.table.setHeader(!context.data.noheader);
        context.data.last = 0;
        
        model(function, context);
        setMode(context);
        setObjects(context);
    }
    
    /**
     * 
     * @param modelname
     */
    private static final void model(Function function, Context context) {
        DocumentModelItem modelitem;
        String name;
        TableToolColumn column;
        DocumentModel model = new Documents(function).
                getModel(context.data.model);
        
        if (model == null)
            throw new RuntimeException(context.data.model.
                    concat(" is an invalid model."));

        context.table.importModel(model);
        for (TableColumn tcolumn : context.table.getColumns()) {
            if (tcolumn.isMark())
                continue;
            
            name = tcolumn.getName();
            column = context.data.columns.get(name);
            if (column == null)
                column = new TableToolColumn(context.data, name);
            
            column.tcolumn = tcolumn;
            modelitem = tcolumn.getModelItem();
            if (modelitem.getSearchHelp() == null)
                modelitem.setSearchHelp(column.sh);
            if (column.size > 0)
                column.tcolumn.setLength(column.size);
        }
    }

}