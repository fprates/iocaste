package org.iocaste.kernel.runtime.shell.sh;

import java.util.List;
import java.util.Map;

import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.documents.common.ValueRange;
import org.iocaste.documents.common.WhereClause;
import org.iocaste.kernel.documents.GetDocumentModel;
import org.iocaste.kernel.documents.SelectDocument;
import org.iocaste.kernel.runtime.shell.PopupData;
import org.iocaste.kernel.runtime.shell.PopupRenderer;
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
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.handlers.ChooseEventHandler;
import org.iocaste.shell.common.handlers.SearchEventHandler;

public class SearchHelpRenderer implements PopupRenderer {
    private Messages messages;
    
    public SearchHelpRenderer() {
        this.messages = new Messages();
    }
    
    private final void addCriteria(Context context, SearchHelp sh,
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
                if (context.popup.viewctx.view.getFocus() == null)
                    context.popup.viewctx.view.setFocus(item);
                continue;
            }
            
            item.setVisible(false);
        }
    }
    
    private final void addItems(Context context,
            Container container, String name, ExtendedObject[] items) {
        TableColumn column;
        TableItem tableitem;
        String export, action, iname, index, choice;
        Object value;
        Text text;
        Link link;
        Parameter param;
        Table table;
        ChooseEventHandler handler;
        int i;
        
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

        export = context.control.getExport();
        handler = new ChooseEventHandler(context.control);
        i = 0;
        for (ExtendedObject object : items) {
            tableitem = new TableItem(table);
            choice = new StringBuilder("choice").append(i++).toString();
            for (DocumentModelItem modelitem : context.model.getItens()) {
                name = modelitem.getName();
                column = table.getColumn(name);
                value = object.get(modelitem);
                index = modelitem.getIndex();
                
                if (export != null && export.equals(index)) {
                    param.setModelItem(modelitem);
                    action = new StringBuilder("setFieldSh('").
                            append(context.control.getInputName()).
                            append("','").
                            append(value).
                            append("');").
                            toString();
                    link = new Link(tableitem, choice, null);
                    link.setText(value.toString());
                    link.setEvent("click", action);
                    link.put("click", handler);
                    handler.put(choice, value);
                } else {
                    text = new Text(tableitem, name);
                    text.setText((value == null)? "" : value.toString());
                }
                
                if (!context.control.contains(index))
                    column.setVisible(false);
            }
            
            SearchHelp.setTableItem(table, tableitem, object);
        }
    }
    
    private final ExtendedObject[] getResultsFrom(
            Context context, SearchEventHandler handler) throws Exception {
        SelectDocument select;
        ValueRange range;
        List<WhereClause> wherelist;
        Query query;
        Object ns;
        String nsreference;
        InputComponent input;

        nsreference = context.control.getNSReference();
        if (nsreference != null) {
            input = context.popup.viewctx.view.getElement(nsreference);
            ns = input.get();
        } else {
            ns = null;
        }

        select = context.popup.viewctx.function.documents.
                get("select_document");
        query = new Query();
        query.setModel(context.model.getName());
        query.setNS(ns);
        if (handler.criteria.size() == 0)
            return select.run(context.popup.connection, query);
        
        for (String name : handler.criteria.keySet()) {
            range = handler.criteria.get(name);
            query.andIn(name, range);
        }
        
        wherelist = query.getWhere();
        for (WhereClause criteria : context.control.getCriteria())
            wherelist.add(criteria);
        
        return select.run(context.popup.connection, query);
    }
    
    private final void response(Context context) throws Exception {
        GetDocumentModel modelget;
        String name, searchjs, searchbt, master, criteriajs;
        ExtendedObject[] result;
        Container datacnt;
        Button button;
        Map<String, String> messages;
        SearchEventHandler searchhandler;
        
        Style.execute(context);
        
        context.popup.container =
                new StandardContainer(context.popup.viewctx.view, "shstdcnt");
        context.popup.container.setStyleClass("shcnt");
        
        button = new Button(context.popup.container, "cancel");
        button.setEvent("click", "closeSh();");
        
        searchbt = context.control.getChild();
        if (searchbt == null)
            searchbt = context.popup.control.getHtmlName();
        
        searchjs = Shell.formSubmit(
                context.popup.form, context.popup.action, searchbt);
        
        searchbt = "bt_".concat(searchbt);
        button = new Button(context.popup.container, searchbt);
        button.addAttribute("style", "display:none");
        button.setEvent("click", searchjs);
        button.put("click", searchhandler = new SearchEventHandler());
        
        criteriajs = new StringBuilder(
            Shell.setElementDisplay(searchbt, "inline")).append(";").
                append(Shell.setElementDisplay("criteria","block")).
                append(";").
                append(Shell.setElementDisplay("bt_criteria","none")).
                append(";").toString();
        
        button = new Button(context.popup.container, "bt_criteria");
        button.setEvent("click", criteriajs);
        master = context.control.getMaster();
        if (master != null)
            context.control = context.popup.viewctx.view.getElement(master);
        
        modelget = context.popup.viewctx.function.documents.
                get("get_document_model");
        name = context.control.getModelName();
        context.model = modelget.run(context.popup.connection,
                context.popup.viewctx.function.documents, name);
        addCriteria(context, context.control, context.popup.container);

        datacnt = new StandardContainer(context.popup.container, "shdatacnt");
        datacnt.setStyleClass("shdatacnt");
        
        try {
            messages = Shell.getMessages(
                    context.popup.viewctx.function,
                    context.popup.viewctx.locale,
                    context.popup.viewctx.view.getAppName());
            if (messages != null) {
                this.messages.instance(context.popup.viewctx.locale);
                for (String key : messages.keySet())
                    this.messages.put(key, messages.get(key));
            }
        } catch (Exception e) {
            /*
             * ignoramos se um programa não tiver traduções. Ex: kernel
             */
        }
        
        this.messages.instance("pt_BR");
        this.messages.put(searchbt, "Selecionar");
        result = getResultsFrom(context, searchhandler);
        if (result == null) {
            new Text(datacnt, "no.results.found");
            context.popup.viewctx.view.setTitle(context.control.getText());
            return;
        }
        
        addItems(context, datacnt, name, result);
    }

    @Override
    public void run(PopupData data) throws Exception {
        ValueRange range;
        InputComponent input;
        DataForm criteria;
        Context context = new Context();
        
        context.popup = data;
        context.control = (SearchHelp)data.control;
//        if (context.control.getMaster() != null) {
//            criteria = context.popup.viewctx.view.getElement("criteria");
//            for (Element element : criteria.getElements()) {
//                input = (InputComponent)element;
//                range = input.get();
//                if (range == null)
//                    continue;
//                context.criteria.put(element.getName(), range);
//            }
//        }
        
        response(context);
    }
}
