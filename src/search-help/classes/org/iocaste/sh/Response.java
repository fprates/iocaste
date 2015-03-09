package org.iocaste.sh;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.Parameter;
import org.iocaste.shell.common.SearchHelp;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.StyleSheet;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.Text;

public class Response {
//    private static final void addCriteria(SearchHelp sh, Container container,
//            DocumentModel model, Context context) {
//        DataItem item;
//        DataForm criteria;
//        
//        criteria = new DataForm(container, "criteria");        
//        criteria.importModel(model);        
//        for (Element element : criteria.getElements()) {
//            if (!element.isDataStorable())
//                continue;
//            
//            item = (DataItem)element;
//            if (sh.contains(item.getName())) {
//                item.setComponentType(Const.RANGE_FIELD);
//                if (context.view.getFocus() == null)
//                    context.view.setFocus(item);
//                continue;
//            }
//            
//            item.setVisible(false);
//        }
//        
//        new Button(container, "search");
//    }
    
    private static final void addItems(Context context, SearchHelp sh,
            Container container, String name, DocumentModel model,
            ExtendedObject[] items) {
        TableColumn column;
        TableItem tableitem;
        String export, action;
        Object value;
        Text text;
        Link link;
        Parameter param;
        Table table;
        
        param = new Parameter(container, "value");
        table = new Table(container, "search.table");
        for (DocumentModelItem item : model.getItens()) {
            column = new TableColumn(table, item.getName());
            column.setMark(false);
            column.setVisible(true);
            column.setModelItem(item);
            column.setLength(item.getDataElement().getLength());
        }
        
        for (ExtendedObject object : items) {
            tableitem = new TableItem(table);
            
            for (DocumentModelItem modelitem : model.getItens()) {
                name = modelitem.getName();
                column = table.getColumn(name);
                value = object.get(modelitem);
                export = sh.getExport();
                
                if (export != null && export.equals(name)) {
                    param.setModelItem(modelitem);
                    action = new StringBuilder(
                            "javascript:setFieldSh('").
                            append(sh.getInputName()).
                            append("','").
                            append(value).
                            append("')").
                            toString();
                    link = new Link(tableitem, "choose", action);
                    link.setText(value.toString());
                    link.setAbsolute(true);
                    link.setStyleClass("shkey");
                } else {
                    column.setRenderTextOnly(true);
                    text = new Text(tableitem, name);
                    text.setText((value == null)? "" : value.toString());
                    text.setStyleClass("shcol");
                }
                
                if (!sh.contains(name))
                    column.setVisible(false);
            }
            
            SearchHelp.setTableItem(context, table, tableitem, object);
        }
    }
    
    public static final void main(Context context) {
        String name;
        DocumentModel model;
        ExtendedObject[] result;
        Container stdcnt, datacnt;
        SearchHelp sh;
        Documents documents = new Documents(context.function);
        StyleSheet stylesheet = context.view.styleSheetInstance();
        
        stylesheet.newElement(".shkey");
        stylesheet.put(".shkey", "font-size", "12pt");
        stylesheet.put(".shkey", "display", "block");
        stylesheet.put(".shkey", "text-decoration", "none");
        
        stylesheet.newElement(".shcol");
        stylesheet.put(".shcol", "font-size", "12pt");
        
        stylesheet.newElement(".shcnt");
        stylesheet.put(".shcnt", "position", "absolute");
        stylesheet.put(".shcnt", "background-color", "#f0f0f0");
        stylesheet.put(".shcnt", "float", "left");
        stylesheet.put(".shcnt", "padding", "10px");
        stylesheet.put(".shcnt", "border-style", "solid");
        stylesheet.put(".shcnt", "border-color", "rgb(176, 176, 176)");
        stylesheet.put(".shcnt", "border-width", "2px"); 
        stylesheet.put(".shcnt", "overflow", "hidden");
        
        stylesheet.newElement(".shdatacnt");
        stylesheet.put(".shdatacnt", "overflow", "auto");
        stylesheet.put(".shdatacnt", "height", "20em");
        
        stdcnt = new StandardContainer(context.view, "shstdcnt");
        stdcnt.setStyleClass("shcnt");
        
        new Button(stdcnt, "cancel").addEvent("onClick","javascript:closeSh()");

        datacnt = new StandardContainer(stdcnt, "shdatacnt");
        datacnt.setStyleClass("shdatacnt");
        
        sh = context.function.getParameter("control");
        name = sh.getModelName();
        model = documents.getModel(name);
//        addCriteria(sh, stdcnt, model, context);
        
        result = Common.getResultsFrom(name, documents, context.criteria);
        if (result == null) {
            new Text(datacnt, "no.results.found");
            context.view.setTitle(sh.getText());
            return;
        }
        
        addItems(context, sh, datacnt, name, model, result);
    }
}
