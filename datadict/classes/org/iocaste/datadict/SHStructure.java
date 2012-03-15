package org.iocaste.datadict;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Function;
import org.iocaste.sh.common.SHLib;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.TextField;
import org.iocaste.shell.common.ViewData;

public class SHStructure {
    private final static String[] TITLE = {
        "sh-editor-create",
        "sh-editor-display",
        "sh-editor-update"
    };
    
    private static String composeName(String model, String item) {
        return new StringBuilder(model).append(".").append(item).toString();
    }
    
    /**
     * 
     * @param mode
     * @param itens
     * @param object
     */
    private static void insertItem(byte mode, Table itens,
            ExtendedObject object) {
        TextField tfield;
        String name;
        TableItem item = new TableItem(itens);
        DocumentModel model = itens.getModel();
        
        for (DocumentModelItem modelitem : model.getItens()) {
            name = modelitem.getName();
            tfield = new TextField(itens, name);
            tfield.setModelItem(modelitem);
            tfield.setReferenceValidable(false);
            tfield.setEnabled((mode == Common.SHOW)? false : true);
            
            if ((mode == Common.SHOW) || name.equals("NAME"))
                tfield.setObligatory(false);
            
            item.add(tfield);
        }
        
        if (object != null)
            item.setObject(object);
    }

    /**
     * 
     * @param view
     * @param function
     */
    public static final void main(ViewData view, Function function)
            throws Exception {
        DataItem ditem;
        String name;
        ExtendedObject[] oitens;
        Container container = new Form(null, "main");
        Documents documents = new Documents(function);
        DocumentModel model = documents.getModel("SEARCH_HELP");
        DataForm header = new DataForm(container, "header");
        Table itens = new Table(container, "itens");
        byte mode = Common.getMode(view);
        
        header.importModel(model);
        
        if (mode != Common.CREATE)
            header.setObject((ExtendedObject)view.getParameter("header"));
        
        for (Element element : header.getElements()) {
            if (element.getType() != Const.DATA_ITEM)
                continue;
            
            ditem = (DataItem)element;
            name = ditem.getName();
            
            if (name.equals("NAME")) {
                ditem.setValue((String)view.getParameter("shname"));
                ditem.setEnabled(false);
                
                continue;
            }
            
            if (name.equals("EXPORT"))
                ditem.setReferenceValidable(false);
            
            ditem.setObligatory((mode == Common.SHOW)? false : true);
            ditem.setEnabled((mode == Common.SHOW)? false : true);
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
                insertItem(mode, itens, item);
            
            break;
            
        case Common.CREATE:
            itens.setMark(true);
            
            insertItem(mode, itens, null);
            
            new Button(container, "saveshitem");
            new Button(container, "addshitem");
            new Button(container, "deleteshitem");
            
            break;
            
        case Common.UPDATE:
            itens.setMark(true);
            
            oitens = view.getParameter("shitens");
            for (ExtendedObject item : oitens)
                insertItem(mode, itens, item);
            
            new Button(container, "saveshitem");
            new Button(container, "addshitem");
            new Button(container, "deleteshitem");
            
            break;
        }
        
        view.setTitle(TITLE[mode]);
        view.setFocus("MODEL");
        view.setNavbarActionEnabled("back", true);
        view.addContainer(container);
    }
    
    /**
     * 
     * @param view
     * @param function
     * @throws Exception
     */
    public static final void save(ViewData view, Function function)
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
            oitens[i] = item.getObject();
        
        switch (mode) {
        case Common.CREATE:
            shlib.save(object, oitens);
            view.export("mode", Common.UPDATE);
            view.setTitle(TITLE[mode]);
            
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
    public static final void insert(ViewData view) {
        Table itens = view.getElement("itens");
        
        insertItem(Common.getMode(view), itens, null);
    }
    
    /**
     * 
     * @param view
     * @param function
     * @return
     * @throws Exception
     */
    public static final boolean validate(ViewData view, Function function)
            throws Exception {
        DataForm header;
        Table itens;
        Element element;
        String model, value;
        Documents documents;
        
        if (!view.getPageName().equals("shstructure"))
            return false;
        
        documents = new Documents(function);
        header = view.getElement("header");
        model = header.get("MODEL").getValue();
        element = header.get("EXPORT");
        value = composeName(model, ((InputComponent)element).getValue());
        
        if (documents.getObject("MODELITEM", value) == null) {
            view.message(Const.ERROR, "invalid.model.item");
            view.setFocus(element);
            
            return true;
        }
        
        itens = view.getElement("itens");
        
        for (TableItem item : itens.getItens()) {
            element = item.get("ITEM");
            
            value = composeName(model, ((InputComponent)element).getValue());
            
            if (documents.getObject("MODELITEM", value) != null)
                continue;
            
            view.message(Const.ERROR, "invalid.model.item");
            view.setFocus(element);
            
            return true;
        }
        
        return false;
    }
}
