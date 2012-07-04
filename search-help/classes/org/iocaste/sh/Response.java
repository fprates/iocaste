package org.iocaste.sh;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Function;
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
import org.iocaste.shell.common.View;

public class Response {

    public static final void main(View view, Function function)
            throws Exception {
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
        Form container = new Form(view, "main");
        SearchHelp sh = view.getParameter("sh");
        Object[] values = view.getParameter("criteria");
        Documents documents = new Documents(function);
        
        new PageControl(container).add("back");
        model = documents.getModel(sh.getModelName());
        criteria = new DataForm(container, "criteria");
        criteria.importModel(model);
        
        for (Element element : criteria.getElements()) {
            if (!element.isDataStorable())
                continue;
            
            item = (DataItem)element;
            if (sh.contains(item.getName())) {
                item.setComponentType(Const.RANGE_FIELD);
                if (view.getFocus() == null)
                    view.setFocus(item);
                continue;
            }
            
            item.setVisible(false);
        }
        
        new Button(container, "search");
        
        if (values == null)
            result = Common.getResultsFrom(sh, documents);
        else
            result = Common.getResultsFrom(sh, documents, values);
        
        if (result == null) {
            text = new Text(container, "no.results.found");
            view.setTitle(sh.getText());
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
                value = object.getValue(modelitem);
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
        
        view.setTitle(sh.getText());
    }
}
