package org.iocaste.dataeditor;

import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolColumn;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.documents.common.Documents;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.PageControl;
import org.iocaste.shell.common.Shell;

public class Response {
    
    /**
     * 
     * @param context
     */
    public static final void form(Context context) {
        DataElement dataelement;
        DataItem item;
        DataForm form;
        Form container = new Form(context.view, "main");
        PageControl pagecontrol = new PageControl(container);
        
        pagecontrol.add("back");
        
        new Button(container, "insertitem");
        new Button(container, "insertnext");
        
        form = new DataForm(container, "model.form");
        form.importModel(new Documents(context.function).
                getModel(context.model));
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
    }

    /**
     * 
     * @param context
     */
    public static final void main(Context context) {
        InputComponent input;
        Form container = new Form(context.view, "main");
        PageControl pagecontrol = new PageControl(container);
        DataForm form = new DataForm(container, "model");
        
        pagecontrol.add("home");
        form.importModel(context.modelmodel);
        for (Element element : form.getElements())
            element.setVisible(false);
        
        input = form.get("NAME");
        input.setObligatory(true);
        input.setVisible(true);
        context.view.setFocus(input);
        
        new Button(container, "display");
        new Button(container, "update");
        
        context.view.setTitle("dataeditor-selection");
    }
    
    /**
     * 
     * @param view
     * @param context
     */
    public static final void output(Context context) {
        DocumentModel model;
        TableToolData ttdata;
        Form container = new Form(context.view, "main");
        PageControl pagecontrol = new PageControl(container);
        Documents documents = new Documents(context.function);
        
        pagecontrol.add("home");
        pagecontrol.add("back");

        ttdata = new TableToolData(container, "itens");
        ttdata.model = context.model;
        ttdata.objects = context.itens;
        ttdata.vlines = 0;
        context.view.setTitle(context.model);

        model = documents.getModel(context.model);
        switch (context.mode) {
        case Context.DISPLAY:
            ttdata.mode = TableTool.DISPLAY;
            for (DocumentModelItem mitem : model.getItens())
                new TableToolColumn(ttdata, mitem.getName()).disabled = true;
            break;
        case Context.UPDATE:
            pagecontrol.add("save", PageControl.REQUEST);
            ttdata.mark = true;
            ttdata.mode = TableTool.UPDATE;
            for (DocumentModelKey key : model.getKeys())
                new TableToolColumn(
                        ttdata, key.getModelItemName()).disabled = true;
            break;
        }
        
        context.tablehelper = new TableTool(context, ttdata);
    }

}
