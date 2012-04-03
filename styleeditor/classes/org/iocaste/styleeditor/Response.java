package org.iocaste.styleeditor;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.ViewData;

public class Response {

    public static final void selection(ViewData view, Function function)
            throws Exception {
        Container container = new Form(view, "main");
        DataForm form = new DataForm(container, "selection");
        DataItem item = new DataItem(form, Const.TEXT_FIELD, "estilo");
        
        item.setModelItem(new Documents(function).getModel("STYLE").
                getModelItem("NAME"));
        
        item.setObligatory(true);
        
        new Button(container, "create");
        new Button(container, "show");
        new Button(container, "update");
        
        view.setFocus("estilo");
        view.setNavbarActionEnabled("back", true);
        view.setTitle("style-editor");
    }
    
    public static final void style(ViewData view, Function function)
            throws Exception {
        Table itens;
        ExtendedObject[] elements = view.getParameter("elements");
        Container container = new Form(view, "main");
        DataForm form = new DataForm(container, "newstyle");
        DataItem style = new DataItem(form, Const.TEXT_FIELD, "style");
        byte mode = Common.getMode(view);
        
        style.setModelItem(new Documents(function).getModel("STYLE").
                getModelItem("NAME"));
        
        switch (mode) {
        case Common.CREATE:
            new Button(container, "addstyle");
            
            itens = new Table(container, "elements");
            new TableColumn(itens, "element");
            
            break;
            
        case Common.SHOW:
            itens = new Table(container, "elements");
            new TableColumn(itens, "element");
            
            for (ExtendedObject element :  elements)
                new TableItem(itens).add(new Link(itens,
                        (String)element.getValue("NAME"), "element"));
            
            break;
        case Common.UPDATE:
            new Button(container, "addstyle");
            
            itens = new Table(container, "elements");
            new TableColumn(itens, "element");
            
            for (ExtendedObject element :  elements)
                new TableItem(itens).add(new Link(itens,
                        (String)element.getValue("NAME"), "element"));
            
            break;
        }
        
        view.setNavbarActionEnabled("back", true);
        view.setFocus(style);
    }
}
