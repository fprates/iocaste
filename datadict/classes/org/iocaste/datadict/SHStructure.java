package org.iocaste.datadict;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.PageControl;
import org.iocaste.shell.common.SHLib;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.TextField;
import org.iocaste.shell.common.ValidatorConfig;
import org.iocaste.shell.common.View;

public class SHStructure {
    private final static String[] TITLE = {
        "sh-editor-create",
        "sh-editor-display",
        "sh-editor-update"
    };
    
    /**
     * 
     * @param mode
     * @param itens
     * @param view
     * @param object
     */
    private static void insertItem(byte mode, Table itens, View view,
            ExtendedObject object) {
        TextField tfield;
        String name;
        TableItem item = new TableItem(itens);
        DocumentModel model = itens.getModel();
        InputComponent modelinput = ((DataForm)view.getElement("header")).
                get("MODEL");
        
        for (DocumentModelItem modelitem : model.getItens()) {
            name = modelitem.getName();
            
            tfield = new TextField(itens, name);
            item.add(tfield);
            tfield.getModelItem().setReference(null);
            
            switch (mode) {
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
                tfield.setValidator(SHItemValidator.class);
                tfield.addValidatorInput(modelinput);
                view.setFocus(tfield);
                modelitem.getDataElement().setLength(24);
            }
        }
        
        if (object != null)
            item.setObject(object);
    }

    /**
     * 
     * @param view
     * @param function
     */
    public static final void main(View view, Function function)
            throws Exception {
        DataItem ditem;
        String name;
        ExtendedObject[] oitens;
        ValidatorConfig validatorcfg;
        Form container = new Form(view, "main");
        PageControl pagecontrol = new PageControl(container);
        Documents documents = new Documents(function);
        DocumentModel model = documents.getModel("SEARCH_HELP");
        DataForm header = new DataForm(container, "header");
        Table itens = new Table(container, "itens");
        byte mode = Common.getMode(view);
        
        pagecontrol.add("home");
        pagecontrol.add("back");
        header.importModel(model);
        
        if (mode != Common.CREATE)
            header.setObject((ExtendedObject)view.getParameter("header"));
        
        validatorcfg = new ValidatorConfig();
        validatorcfg.setValidator(SHExportValidator.class);
        
        for (Element element : header.getElements()) {
            if (element.getType() != Const.DATA_ITEM)
                continue;
            
            ditem = (DataItem)element;
            name = ditem.getName();
            
            if (name.equals("NAME")) {
                ditem.set(view.getParameter("shname"));
                ditem.setEnabled(false);
                
                continue;
            }
            
            if (name.equals("MODEL")) {
                validatorcfg.add(ditem);
                view.setFocus(ditem);
            }
            
            if (name.equals("EXPORT")) {
                validatorcfg.add(ditem);
                
                ditem.getModelItem().getDataElement().setLength(24);
                ditem.getModelItem().setReference(null);
                ditem.setValidator(SHItemValidator.class);
            }
            
            ditem.setObligatory(mode != Common.SHOW);
            ditem.setEnabled(mode != Common.SHOW);
        }
        
        model = documents.getModel("SH_ITENS");
        
        itens.importModel(model);
        itens.getColumn("NAME").setVisible(false);
        itens.getColumn("SEARCH_HELP").setVisible(false);
        
        switch (mode) {
        case Common.SHOW:
            itens.setMark(false);
            
            oitens = view.getParameter("itens");
            for (ExtendedObject item : oitens)
                insertItem(mode, itens, view, item);
            
            break;
            
        case Common.CREATE:
            itens.setMark(true);
            
            insertItem(mode, itens, view, null);
            
            new Button(container, "savesh");
            new Button(container, "addshitem");
            new Button(container, "deleteshitem");
            
            break;
            
        case Common.UPDATE:
            itens.setMark(true);
            
            oitens = view.getParameter("itens");
            for (ExtendedObject item : oitens)
                insertItem(mode, itens, view, item);
            
            new Button(container, "savesh");
            new Button(container, "addshitem");
            new Button(container, "deleteshitem");
            
            break;
        }
        
        view.setTitle(TITLE[mode]);
    }
    
    /**
     * 
     * @param view
     * @param function
     * @throws Exception
     */
    public static final void save(View view, Function function)
            throws Exception {
        ExtendedObject[] oitens;
        int i = 0;
        SHLib shlib = new SHLib(function);
        DataForm header = view.getElement("header");
        ExtendedObject object = header.getObject();
        Table itens = view.getElement("itens");
        oitens = new ExtendedObject[itens.length()];
        byte mode = Common.getMode(view);
        
        for (TableItem item : itens.getItens())
            oitens[i++] = item.getObject();
        
        switch (mode) {
        case Common.CREATE:
            shlib.save(object, oitens);
            view.export("mode", Common.UPDATE);
            view.setTitle(TITLE[Common.UPDATE]);
            
            break;
        case Common.UPDATE:
            shlib.update(object, oitens);
            
            break;
        }
        
        view.message(Const.STATUS, "search.help.saved.successfully");
    }
    
    /**
     * 
     * @param view
     */
    public static final void insert(View view) {
        Table itens = view.getElement("itens");
        
        insertItem(Common.getMode(view), itens, view, null);
    }
}
