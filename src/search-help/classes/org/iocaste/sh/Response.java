package org.iocaste.sh;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.Parameter;
import org.iocaste.shell.common.SearchHelp;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.Text;

public class Response {
    private static final void addCriteria(SearchHelp sh, Container container,
            DocumentModel model, Context context) {
        DataItem item;
        DataForm criteria;
        
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
    }
    
    private static final void addItems(SearchHelp sh, Container container,
            String name, DocumentModel model, ExtendedObject[] items) {
        TableColumn column;
        TableItem tableitem;
        String export;
        Object value;
        Text text;
        Link link;
        Parameter param;
        Table table = new Table(container, "search.table");
        table.importModel(model);
        param = new Parameter(container, "value");
        
        for (ExtendedObject object : items) {
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
    }
    
    public static final void main(Context context) {
        String name;
        DocumentModel model;
        ExtendedObject[] result;
        StandardContainer stdcnt;
        SearchHelp sh;
        Documents documents = new Documents(context.function);
        Map<String, Map<String, String>> stylesheet = context.view.
                getStyleSheet();
        Map<String, String> style = new HashMap<>();
        
        style.put("background-color", "black");
        style.put("float", "left");
        stylesheet.put(".shcnt", style);
        stdcnt = new StandardContainer(context.view, "shstdcnt");
        stdcnt.setStyleClass("shcnt");

        sh = context.view.getParameter("sh");
        name = sh.getModelName();
        model = documents.getModel(name);
        addCriteria(sh, stdcnt, model, context);
        
        result = Common.getResultsFrom(name, documents, context.criteria);
        if (result == null) {
            new Text(stdcnt, "no.results.found");
            context.view.setTitle(sh.getText());
            return;
        }
        
        addItems(sh, stdcnt, name, model, result);
        context.view.setTitle(sh.getText());
    }
}
