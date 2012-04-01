package org.iocaste.dataview;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.ViewData;

public class Response {
    
    /**
     * 
     * @param vdata
     * @throws Exception
     */
    public static final void form(ViewData vdata, Function function)
            throws Exception {
        DataItem item;
        Container container = new Form(vdata, "form");
        DataForm form = new DataForm(container, "model.form");
        Documents documents = new Documents(function);
        DocumentModel model = documents.getModel(
                (String)vdata.getParameter("model.name"));
        
        form.importModel(model);
        form.setKeyRequired(true);
        
        item = form.get("UPCASE");
        item.setComponentType(Const.CHECKBOX);
        
        new Button(container, "insertitem");
        new Button(container, "insertnext");
        
        vdata.setNavbarActionEnabled("back", true);
    }

    /**
     * 
     * @param view
     * @param function
     * @throws Exception
     */
    public static final void main(ViewData view, Function function)
            throws Exception {
        Container container = new Form(view, "main");
        DataForm form = new DataForm(container, "model");
        DataItem formitem = new DataItem(form, Const.TEXT_FIELD, "model.name");
        
        formitem.setModelItem(new Documents(function).getModel("MODEL").
                getModelItem("NAME"));
        formitem.setObligatory(true);
        new Button(container, "edit");
//        form.addAction("show");
        
        view.setFocus("model.name");
        view.setTitle("dataview-selection");
        view.setNavbarActionEnabled("back", true);
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public static final void select(ViewData view, Function function)
            throws Exception {
        boolean key;
        Container container = new Form(view, "dataview.container");
        ExtendedObject[] itens = view.getParameter("model.regs");
        Documents documents = new Documents(function);
        String modelname = view.getParameter("model.name");
        DocumentModel model = documents.getModel(modelname);
        Table table = new Table(container, "selection_view");
        Const viewtype = view.getParameter("view.type");
        
        table.setMark(true);
        table.importModel(model);
        
        for (TableColumn column: table.getColumns()) {
            if (column.isMark())
                continue;
            
            key = model.isKey(column.getModelItem());
            if (!key && (viewtype == Const.DETAILED))
                column.setVisible(false);
        }
        
        if (itens != null)
            for (ExtendedObject item : itens)
                Common.addTableItem(table, item);
        
        new Button(container, "save");
        new Button(container, "insert");
        new Button(container, "delete");
//        new Button(container, "firstpage").setSubmit(true);
//        new Button(container, "earlierpage").setSubmit(true);
//        new Button(container, "laterpage").setSubmit(true);
//        new Button(container, "lastpage").setSubmit(true);
        
        view.setTitle(modelname);
        view.setNavbarActionEnabled("back", true);
    }

}
