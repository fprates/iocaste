package org.iocaste.dataview;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.PageControl;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.View;

public class Response {
    
    /**
     * 
     * @param vdata
     */
    public static final void form(View vdata, Function function) {
        DataElement dataelement;
        DataItem item;
        Form container = new Form(vdata, "form");
        PageControl pagecontrol = new PageControl(container);
        DataForm form = new DataForm(container, "model.form");
        Documents documents = new Documents(function);
        DocumentModel model = documents.getModel(
                (String)vdata.getParameter("model.name"));
        
        pagecontrol.add("back");
        
        form.importModel(model);
        form.setKeyRequired(true);
        
        for (Element element : form.getElements()) {
            if (!element.isDataStorable())
                continue;
            
            item = (DataItem)element;
            dataelement = Shell.getDataElement(item);
            if (dataelement.getType() != DataType.BOOLEAN)
                continue;
            
            item.setComponentType(Const.CHECKBOX);
        }
        
        new Button(container, "insertitem");
        new Button(container, "insertnext");
    }

    /**
     * 
     * @param view
     * @param function
     */
    public static final void main(View view, Function function) {
        Form container = new Form(view, "main");
        PageControl pagecontrol = new PageControl(container);
        DataForm form = new DataForm(container, "model");
        DataItem formitem = new DataItem(form, Const.TEXT_FIELD, "model.name");
        
        pagecontrol.add("home");
        
        formitem.setModelItem(new Documents(function).getModel("MODEL").
                getModelItem("NAME"));
        formitem.setObligatory(true);
        view.setFocus(formitem);
        
        new Button(container, "edit");
//        form.addAction("show");
        
        view.setTitle("dataview-selection");
    }
    
    /**
     * 
     * @param view
     */
    public static final void select(View view, Function function) {
        boolean key;
        Form container = new Form(view, "dataview.container");
        PageControl pagecontrol = new PageControl(container);
        ExtendedObject[] itens = view.getParameter("model.regs");
        Documents documents = new Documents(function);
        String modelname = view.getParameter("model.name");
        DocumentModel model = documents.getModel(modelname);
        Table table = new Table(container, "selection_view");
        Const viewtype = view.getParameter("view.type");
        
        pagecontrol.add("home");
        pagecontrol.add("back");
        
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
    }

}
