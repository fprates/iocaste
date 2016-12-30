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
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.Parameter;
import org.iocaste.shell.common.SearchHelp;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.StyleSheet;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.View;

public class Response {
    
    private static final void addCriteria(Context context, SearchHelp sh,
            Container container) {
        DataItem item;
        DataForm criteria;
        
        criteria = new DataForm(container, "criteria");
        criteria.setStyleClass("shcriteria");
        criteria.addAttribute("style", "display:none");
        DataForm.importModel(criteria, context.model);
        
        for (Element element : criteria.getElements()) {
            if (!element.isDataStorable())
                continue;
            
            item = (DataItem)element;
            if (sh.contains(item.getModelItem().getIndex())) {
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
        String export, action, iname, index;
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

        export = sh.getExport();
        for (ExtendedObject object : items) {
            tableitem = new TableItem(table);
            
            for (DocumentModelItem modelitem : context.model.getItens()) {
                name = modelitem.getName();
                column = table.getColumn(name);
                value = object.get(modelitem);
                index = modelitem.getIndex();
                
                if (export != null && export.equals(index)) {
                    param.setModelItem(modelitem);
                    action = new StringBuilder("setFieldSh('").
                            append(sh.getInputName()).
                            append("','").
                            append(value).
                            append("');").
                            toString();
                    link = new Link(tableitem, "choose", null);
                    link.setText(value.toString());
                    link.setEvent("click", action);
                } else {
                    text = new Text(tableitem, name);
                    text.setText((value == null)? "" : value.toString());
                }
                
                if (!sh.contains(index))
                    column.setVisible(false);
            }
            
            SearchHelp.setTableItem(context, table, tableitem, object);
        }
    }
    
    public static final void main(Context context) {
        View view;
        String name, searchjs, action, form, searchbt, master, criteriajs;
        String locale;
        ExtendedObject[] result;
        Container stdcnt, datacnt;
        SearchHelp sh;
        Button select, criteria;
        Map<String, String> style, messages;
        Documents documents = new Documents(context.function);
        StyleSheet stylesheet = StyleSheet.instance(context.view);
        Map<Integer, String> constants = stylesheet.getConstants();
        
        style = stylesheet.newElement(".shcnt");
        style.put("position", "absolute");
        style.put("background-color", constants.get(Shell.BACKGROUND_COLOR));
        style.put("float", "left");
        style.put("padding", "10px");
        style.put("border-style", "solid");
        style.put("border-color", constants.get(Shell.FRAME_COLOR));
        style.put("border-width", "1px");
        style.put("overflow", "hidden");
        style.put("z-index", "1");
        style.put("margin-top", "5px");
        style.put("margin-left", "5px");
        style.put("box-shadow", constants.get(Shell.SHADOW));
        
        style = stylesheet.newElement(".shdatacnt");
        style.put("overflow", "auto");
        style.put("height", "20em");
        
        style = stylesheet.newElement(".shcriteria");
        style.put("margin", "0px");
        style.put("padding", "0px");
        style.put("border-style", "none");
        
        stdcnt = new StandardContainer(context.view, "shstdcnt");
        stdcnt.setStyleClass("shcnt");
        
        new Button(stdcnt, "cancel").setEvent("click", "closeSh();");
        
        action = context.function.getParameter("action");
        form = context.function.getParameter("form");
        
        searchbt = context.control.getChild();
        if (searchbt == null)
            searchbt = context.control.getHtmlName();
        
        searchjs = new StringBuilder("formSubmit('").
                append(form).append("', '").
                append(action).append("', '").
                append(searchbt).append("');").toString();
        
        searchbt = "bt_".concat(searchbt);
        select = new Button(stdcnt, searchbt);
        select.addAttribute("style", "display:none");
        select.setEvent("click", searchjs);
        
        criteriajs = new StringBuilder("setElementDisplay('").append(searchbt).
                append("', 'inline');setElementDisplay('criteria','block');"
                        + "setElementDisplay('bt_criteria','none');").
                toString();
        criteria = new Button(stdcnt, "bt_criteria");
        criteria.setEvent("click", criteriajs);
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
        
        locale = view.getLocale().toString();
        try {
            messages = Shell.getMessages(context.function, locale,
                    view.getAppName());
            if (messages != null) {
                context.messages.instance(locale);
                for (String key : messages.keySet())
                    context.messages.put(key, messages.get(key));
            }
        } catch (Exception e) {
            /*
             * ignoramos se um programa não tiver traduções. Ex: kernel
             */
        }
        
        context.messages.instance("pt_BR");
        context.messages.put(searchbt, "Selecionar");
        result = Common.getResultsFrom(documents, context, sh);
        if (result == null) {
            new Text(datacnt, "no.results.found");
            context.view.setTitle(sh.getText());
            return;
        }
        
        addItems(context, sh, datacnt, name, result);
    }
}
