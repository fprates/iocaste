package org.iocaste.datadict;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.PageControl;
import org.iocaste.shell.common.SHLib;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.TextField;
import org.iocaste.shell.common.Validator;

public class SHStructure {
    private final static String[] TITLE = {
        "sh-editor-create",
        "sh-editor-display",
        "sh-editor-update"
    };
    
    private static void insertItem(Context context, Table itens,
            ExtendedObject object) {
        Validator validator;
        TextField tfield;
        String name;
        TableItem item = new TableItem(itens);
        DocumentModel model = itens.getModel();
        
        validator = new SHItemValidator();
        ((AbstractPage)context.function).register(validator);
        for (DocumentModelItem modelitem : model.getItens()) {
            name = modelitem.getName();
            
            tfield = new TextField(itens, name);
            item.add(tfield);
            tfield.getModelItem().setReference(null);
            
            switch (context.mode) {
            case Common.SHOW:
                tfield.setEnabled(false);
                if (name.equals("NAME"))
                    tfield.setObligatory(false);
                
                break;
                
            default:
                tfield.setEnabled(true);
                break;
            }
            
            if (name.equals("ITEM")) {
                tfield.setValidator(validator);
                
                context.view.setFocus(tfield);
                modelitem.getDataElement().setLength(24);
            }
        }
        
        if (object != null)
            item.setObject(object);
    }

    public static final void main(Context context) {
        Validator validator;
        DataItem ditem;
        String name;
        ExtendedObject[] oitens;
        Form container = new Form(context.view, "main");
        PageControl pagecontrol = new PageControl(container);
        Documents documents = new Documents(context.function);
        DocumentModel model = documents.getModel("SEARCH_HELP");
        DataForm header = new DataForm(container, "header");
        Table itens = new Table(container, "itens");
        
        pagecontrol.add("home");
        pagecontrol.add("back");
        header.importModel(model);
        
        if (context.mode != Common.CREATE)
            header.setObject((ExtendedObject)context.view.
                    getParameter("header"));
        
        validator = new SHExportValidator();
        ((AbstractPage)context.function).register(validator);
        for (Element element : header.getElements()) {
            if (element.getType() != Const.DATA_ITEM)
                continue;
            
            ditem = (DataItem)element;
            name = ditem.getName();
            
            if (name.equals("NAME")) {
                ditem.set(context.view.getParameter("shname"));
                ditem.setEnabled(false);
                
                continue;
            }
            
            if (name.equals("MODEL"))
                context.view.setFocus(ditem);
            
            if (name.equals("EXPORT")) {
                ditem.getModelItem().getDataElement().setLength(24);
                ditem.getModelItem().setReference(null);
                ditem.setValidator(validator);
            }
            
            ditem.setObligatory(context.mode != Common.SHOW);
            ditem.setEnabled(context.mode != Common.SHOW);
        }
        
        model = documents.getModel("SH_ITENS");
        
        itens.importModel(model);
        itens.getColumn("NAME").setVisible(false);
        itens.getColumn("SEARCH_HELP").setVisible(false);
        
        switch (context.mode) {
        case Common.SHOW:
            itens.setMark(false);
            
            oitens = context.view.getParameter("itens");
            for (ExtendedObject item : oitens)
                insertItem(context, itens, item);
            
            break;
            
        case Common.CREATE:
            itens.setMark(true);
            
            insertItem(context, itens, null);
            
            new Button(container, "savesh");
            new Button(container, "addshitem");
            new Button(container, "deleteshitem");
            
            break;
            
        case Common.UPDATE:
            itens.setMark(true);
            
            oitens = context.view.getParameter("itens");
            for (ExtendedObject item : oitens)
                insertItem(context, itens, item);
            
            new Button(container, "savesh");
            new Button(container, "addshitem");
            new Button(container, "deleteshitem");
            
            break;
        }
        
        context.view.setTitle(TITLE[context.mode]);
    }
    
    public static final void save(Context context) {
        ExtendedObject[] oitens;
        int i = 0;
        SHLib shlib = new SHLib(context.function);
        DataForm header = context.view.getElement("header");
        ExtendedObject object = header.getObject();
        Table itens = context.view.getElement("itens");
        oitens = new ExtendedObject[itens.length()];
        
        for (TableItem item : itens.getItems())
            oitens[i++] = item.getObject();
        
        switch (context.mode) {
        case Common.CREATE:
            shlib.save(object, oitens);
            context.mode = Common.UPDATE;
            context.view.setTitle(TITLE[Common.UPDATE]);
            
            break;
        case Common.UPDATE:
            shlib.update(object, oitens);
            
            break;
        }
        
        context.view.message(Const.STATUS, "search.help.saved.successfully");
    }
    
    /**
     * 
     * @param view
     */
    public static final void insert(Context context) {
        Table itens = context.view.getElement("itens");
        
        insertItem(context, itens, null);
    }
}
