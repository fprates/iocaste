package org.iocaste.styleeditor;

import org.iocaste.documents.common.Documents;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.PageControl;
import org.iocaste.shell.common.SearchHelp;
import org.iocaste.shell.common.TableTool;
import org.iocaste.shell.common.TableToolData;

public class Response {

    public static final void detail(Context context) {
        TableToolData ttdata;
        Form container = new Form(context.view, "main");
        Documents documents = new Documents(context.function);
        PageControl pagecontrol = new PageControl(container);
        
        pagecontrol.add("back");
        ttdata = new TableToolData();
        ttdata.context = context;
        ttdata.container = container;
        ttdata.name = "details";
        context.properties = new TableTool(ttdata);
        context.properties.model(documents.getModel("STYLE_ELEMENT_DETAIL"));
        context.properties.setObjects(context.eproperties);
        context.properties.setVisibleLines(0);
        switch (context.mode) {
        case Context.SHOW:
            context.properties.setMode(TableTool.DISPLAY);
            context.properties.setVisibility(false, "INDEX", "ELEMENT");
            break;
        case Context.UPDATE:
            context.properties.setMode(TableTool.UPDATE);
            context.properties.controls(TableTool.DISABLED);
            context.properties.setVisibility(false, "INDEX", "ELEMENT");
            context.properties.setColumnStatus(TableTool.DISABLED, "PROPERTY");
            break;
        }
        context.view.setTitle(context.element);
    }
    
    public static final void selection(Context context) {
        SearchHelp sh;
        Form container = new Form(context.view, "main");
        PageControl pagecontrol = new PageControl(container);
        DataForm form = new DataForm(container, "selection");
        DataItem item = new DataItem(form, Const.TEXT_FIELD, "estilo");
        
        pagecontrol.add("home");
        item.setModelItem(new Documents(context.function).getModel("STYLE").
                getModelItem("NAME"));
        
        sh = new SearchHelp(form, "SH_STYLE");
        sh.setModelName("STYLE");
        sh.setExport("NAME");
        sh.addModelItemName("NAME");
        item.setSearchHelp(sh);
        
        item.setObligatory(true);
        
        new Button(container, "create");
        new Button(container, "show");
        new Button(container, "update");
        
        context.view.setFocus(item);
        context.view.setTitle("style-editor");
    }
    
    public static final void style(Context context) {
        TableToolData ttdata;
        Form container = new Form(context.view, "main");
        PageControl pagecontrol = new PageControl(container);
        DataForm header = new DataForm(container, "header");
        Documents documents = new Documents(context.function);
        
        pagecontrol.add("back");
        header.importModel(documents.getModel("STYLE"));
        header.setObject(context.header);
        for (Element element : header.getElements()) {
            if (!element.getName().equals("NAME"))
                element.setVisible(false);
            ((InputComponent)element).setEnabled(false);
        }

        ttdata = new TableToolData();
        ttdata.context = context;
        ttdata.container = container;
        ttdata.name = "elements";
        context.items = new TableTool(ttdata);
        context.items.model(documents.getModel("STYLE_ELEMENT"));
        context.items.setVisibility(false, "INDEX", "STYLE");
        context.items.setColumnType("NAME", Const.LINK, "element");
        context.items.setVisibleLines(0);
        context.items.setMode(TableTool.UPDATE);
        context.items.controls(TableTool.DISABLED);
        
        switch (context.mode) {
        case Context.CREATE:
            pagecontrol.add("save", PageControl.REQUEST);
            break;
        case Context.UPDATE:
            pagecontrol.add("save", PageControl.REQUEST);
        default:
            context.items.setObjects(context.elements);
            break;
        }
    }
}
