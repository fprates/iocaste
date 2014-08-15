package org.iocaste.styleeditor;

import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolColumn;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
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

public class Response {

    public static final void detail(Context context) {
        TableToolData ttdata;
        Form container = new Form(context.view, "main");
        PageControl pagecontrol = new PageControl(container);
        
        pagecontrol.add("back");
        ttdata = new TableToolData(container, "details");
        ttdata.model = "STYLE_ELEMENT_DETAIL";
        ttdata.objects = context.eproperties;
        ttdata.vlines = 0;
        switch (context.mode) {
        case Context.SHOW:
            ttdata.mode = TableTool.DISPLAY;
            ttdata.hide= new String[] {"INDEX", "ELEMENT"};
            break;
        case Context.UPDATE:
            ttdata.mode = TableTool.UPDATE;
            ttdata.hide = new String[] {"INDEX", "ELEMENT"};
            new TableToolColumn(ttdata, "PROPERTY").disabled = true;
            break;
        }
        
        context.properties = new TableTool(context, ttdata);
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
        TableToolColumn column;
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

        ttdata = new TableToolData(container, "elements");
        ttdata.model = "STYLE_ELEMENT";
        ttdata.hide = new String[] {"INDEX", "STYLE"};
        ttdata.vlines = 0;
        ttdata.mode = TableTool.UPDATE;
        column = new TableToolColumn(ttdata, "NAME");
        column.type = Const.LINK;
        column.name = "element";
        
        switch (context.mode) {
        case Context.CREATE:
        case Context.UPDATE:
            pagecontrol.add("save", PageControl.REQUEST);
        default:
            ttdata.objects = context.elements;
            break;
        }
        
        context.items = new TableTool(context, ttdata);
    }
}
