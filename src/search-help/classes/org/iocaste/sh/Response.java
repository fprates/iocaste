package org.iocaste.sh;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.PageControl;
import org.iocaste.shell.common.Parameter;
import org.iocaste.shell.common.SearchHelp;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.Text;

public class Response {

    public static final void main(Context context) {
        ExtendedObject[] result;
        DataForm criteria;
        DataItem item;
        TableColumn column;
        TableItem tableitem;
        String name, export;
        Object value;
        Text text;
        Link link;
        DocumentModel model;
        Table table;
        Parameter param;
        Form container = new Form(context.view, "main");
        SearchHelp sh = context.view.getParameter("sh");
        Documents documents = new Documents(context.function);
        
        new PageControl(container).add("back");
        name = sh.getModelName();
        model = documents.getModel(name);
        criteria = new DataForm(container, "criteria");
        criteria.importModel(model);
        
        for (Element element : criteria.getElements()) {
            if (!element.isDataStorable())
                continue;
            
            item = (DataItem)element;
            if (sh.contains(item.getName())) {
                item.setComponentType(Const.RANGE_FIELD);
                if (context.view.getFocus() == null)
                    context.view.setFocus(item);
                continue;
            }
            
            item.setVisible(false);
        }
        
        new Button(container, "search");
        
        result = Common.getResultsFrom(name, documents, context.criteria);
        if (result == null) {
            text = new Text(container, "no.results.found");
            context.view.setTitle(sh.getText());
            return;
        }
        
        table = new Table(container, "search.table");
        table.importModel(model);
        param = new Parameter(container, "value");
        
        for (ExtendedObject object : result) {
            tableitem = new TableItem(table);
            
            for (DocumentModelItem modelitem : model.getItens()) {
                name = modelitem.getName();
                column = table.getColumn(name);
                value = object.get(modelitem);
                export = sh.getExport();
                
                if (export != null && export.equals(name)) {
                    param.setModelItem(modelitem);
                    link = new Link(table, "choose", "choose");
                    tableitem.add(link);
                    link.add(param, value);
                    link.setText(value.toString());
                } else {
                    column.setRenderTextOnly(true);
                    text = new Text(table, name);
                    tableitem.add(text);
                    text.setText((value == null)? "" : value.toString());
                }
                
                if (!sh.contains(name))
                    column.setVisible(false);
            }
            
            tableitem.setObject(object);
        }
        
        context.view.setTitle(sh.getText());
    }
}
