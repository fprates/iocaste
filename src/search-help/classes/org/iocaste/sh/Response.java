package org.iocaste.sh;

import java.util.Map;

import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.Parameter;
import org.iocaste.shell.common.SearchHelp;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.StyleSheet;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.View;

public class Response {
    private static final String BODY = "#ffffff";
    private static final String BORDER = "#a0a0a0";
    
    private static final void addCriteria(Context context, SearchHelp sh,
            Container container) {
        String name;
        DataItem item;
        DataForm criteria;
        
        criteria = new DataForm(container, "criteria");
        criteria.setStyleClass("shcriteria");
        criteria.importModel(context.model);
        
        for (Element element : criteria.getElements()) {
            if (!element.isDataStorable())
                continue;
            
            item = (DataItem)element;
            name = item.getName();
            if (sh.contains(name)) {
                item.setComponentType(Const.RANGE_FIELD);
                if (context.view.getFocus() == null)
                    context.view.setFocus(item);
                continue;
            }
            
            item.setVisible(false);
        }
    }
    
    private static final void addItems(Context context, SearchHelp sh,
            Container container, String name, ExtendedObject[] items) {
        TableColumn column;
        TableItem tableitem;
        String export, action, iname;
        Object value;
        Text text;
        Link link;
        Parameter param;
        Table table;
        
        param = new Parameter(container, "value");
        table = new Table(container, "search.table");
        for (DocumentModelItem item : context.model.getItens()) {
            iname = item.getName();
            column = new TableColumn(table, iname);
            column.setMark(false);
            column.setVisible(true);
            column.setModelItem(item);
            column.setLength(item.getDataElement().getLength());
        }
        
        for (ExtendedObject object : items) {
            tableitem = new TableItem(table);
            
            for (DocumentModelItem modelitem : context.model.getItens()) {
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
        View view;
        String name, searchjs, action, form, searchbt, master, nsreference;
        ExtendedObject[] result;
        Container stdcnt, datacnt;
        SearchHelp sh;
        Object ns;
        Map<String, String> style;
        Documents documents = new Documents(context.function);
        StyleSheet stylesheet = context.view.styleSheetInstance();
        
        style = stylesheet.newElement(".shkey");
        style.put("font-size", "12pt");
        style.put("display", "block");
        style.put("text-decoration", "none");
        
        stylesheet.newElement(".shcol").put("font-size", "12pt");
        
        style = stylesheet.newElement(".shcnt");
        style.put("position", "absolute");
        style.put("background-color", BODY);
        style.put("float", "left");
        style.put("padding", "10px");
        style.put("border-style", "solid");
        style.put("border-color", BORDER);
        style.put("border-width", "2px"); 
        style.put("overflow", "hidden");
        
        style = stylesheet.newElement(".shdatacnt");
        style.put("overflow", "auto");
        style.put("height", "20em");
        
        style = stylesheet.newElement(".shcriteria");
        style.put("margin", "0px");
        style.put("padding", "0px");
        style.put("border-style", "none");
        
        stdcnt = new StandardContainer(context.view, "shstdcnt");
        stdcnt.setStyleClass("shcnt");
        
        new Button(stdcnt, "cancel").addEvent("onClick","javascript:closeSh()");
        
        action = context.function.getParameter("action");
        form = context.function.getParameter("form");
        
        searchbt = context.control.getChild();
        if (searchbt == null)
            searchbt = context.control.getHtmlName();
        
        searchjs = new StringBuilder("javascript:formSubmit('").
                append(form).append("', '").
                append(action).append("', '").
                append(searchbt).append("');").toString();
        
        searchbt = "bt_".concat(searchbt);
        new Button(stdcnt, searchbt).addEvent("onClick", searchjs);
        context.messages.put(searchbt, "Selecionar");
        
        sh = context.control;
        master = sh.getMaster();
        view = context.control.getView();
        if (master != null)
            sh = view.getElement(master);
        
        name = sh.getModelName();
        context.model = documents.getModel(name);
        addCriteria(context, sh, stdcnt);

        datacnt = new StandardContainer(stdcnt, "shdatacnt");
        datacnt.setStyleClass("shdatacnt");
        nsreference = sh.getNSReference();
        if (nsreference != null)
            ns = ((InputComponent)view.getElement(nsreference)).get();
        else
            ns = null;
        
        result = Common.getResultsFrom(documents, context, ns);
        if (result == null) {
            new Text(datacnt, "no.results.found");
            context.view.setTitle(sh.getText());
            return;
        }
        
        addItems(context, sh, datacnt, name, result);
    }
}
