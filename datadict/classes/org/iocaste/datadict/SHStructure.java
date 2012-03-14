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
    
    /**
     * 
     * @param mode
     * @param itens
     */
    private static void insertItem(byte mode, Table itens) {
        TextField tfield;
        TableItem item = new TableItem(itens);
        DocumentModel model = itens.getModel();
        
        for (DocumentModelItem modelitem : model.getItens()) {
            tfield = new TextField(itens, modelitem.getName());
            tfield.setModelItem(modelitem);
            tfield.setReferenceValidable(false);
            tfield.setObligatory((mode == Common.SHOW)? false : true);
            
            item.add(tfield);
        }
    }

    /**
     * 
     * @param view
     * @param function
     */
    public static final void main(ViewData view, Function function) {
        DataItem ditem;
        Container container = new Form(null, "main");
        DocumentModel model = view.getParameter("shmodel");
        DataForm header = new DataForm(container, "header");
        Table itens = new Table(container, "itens");
        
        header.importModel(model);
        
        for (Element element : header.getElements()) {
            if (element.getType() != Const.DATA_ITEM)
                continue;
            
            ditem = (DataItem)element;
            
            if (ditem.getName().equals("NAME")) {
                ditem.setValue((String)view.getParameter("shname"));
                ditem.setEnabled(false);
                
                continue;
            }
            
            ditem.setObligatory(true);
        }
        
        model = view.getParameter("shitens");
        itens.importModel(model);
        itens.getColumn("SEARCH_HELP").setVisible(false);
        itens.setMark(true);
        
        insertItem(Common.getMode(view), itens);
        
        new Button(container, "saveshitem");
        new Button(container, "addshitem");
        new Button(container, "deleteshitem");
        
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
        for (TableItem item : itens.getItens())
            oitens[i] = item.getObject();
          
        shlib.save(object, oitens);
          
        view.message(Const.STATUS, "search.help.saved.successfully");
    }
    
    /**
     * 
     * @param view
     */
    public static final void insert(ViewData view) {
        Table itens = view.getElement("itens");
        
        insertItem(Common.getMode(view), itens);
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
        itens = view.getElement("itens");
        
        for (TableItem item : itens.getItens()) {
            element = item.get("ITEM");
            
            value = ((InputComponent)element).getValue();
            if (value.equals(""))
                continue;
            
            value = new StringBuilder(model).append(".").
                    append(value).toString();
            
            if (documents.getObject("MODELITEM", value) != null)
                continue;
            
            view.message(Const.ERROR, "invalid.model.item");
            view.setFocus(element);
            
            return true;
        }
        
        return false;
    }
}
