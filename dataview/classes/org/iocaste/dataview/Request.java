package org.iocaste.dataview;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.ViewData;

public class Request {
    
    /**
     * 
     * @param vdata
     * @param function
     * @throws Exception
     */
    public static final void delete(ViewData vdata, Function function)
            throws Exception {
        Table table = (Table)vdata.getElement("selection_view");
        Documents documents = new Documents(function);
        
        for (TableItem item : table.getItens()) {
            if (!item.isSelected())
                continue;
            
            if (documents.delete(item.getObject()) == 0) {
                vdata.message(Const.ERROR, "error.on.delete");
                return;
            }

            documents.commit();
            table.remove(item);
        }
        
        vdata.message(Const.STATUS, "delete.sucessful");
    }
    
    /**
     * 
     * @param vdata
     * @param function
     */
    public static final void edit(ViewData vdata, Function function) {
        ExtendedObject[] itens;
        String modelname = ((InputComponent)vdata.
                getElement("model.name")).getValue();
        
        try {
            itens = getTableItens(modelname, function);
        } catch (Exception e) {
            vdata.message(Const.ERROR, e.getMessage());
            return;
        }
        
        vdata.clearParameters();
        vdata.export("mode", "edit");
        vdata.export("view.type", Const.SINGLE);
        vdata.export("model.name", modelname);
        vdata.export("model.regs", itens);
        vdata.setReloadableView(true);
        vdata.redirect(null, "select");
    }
    
    /**
     * 
     * @param name
     * @return
     * @throws Exception
     */
    private static final ExtendedObject[] getTableItens(String name,
            Function function) throws Exception {
        ExtendedObject[] itens;
        String query;
        Documents documents = new Documents(function);
        
        if (!documents.hasModel(name))
            throw new Exception("invalid.model");
            
        query = new StringBuilder("from ").append(name).toString();
        itens = documents.select(query, null);
        
        if (itens == null)
            throw new Exception("table.is.empty");
        
        return itens;
    }
    
    /**
     * 
     * @param vdata
     */
    public static final void insert(ViewData vdata) {
        String modelname = (String)vdata.getParameter("model.name");
        
        vdata.clearParameters();
        vdata.export("model.name", modelname);
        vdata.setReloadableView(true);
        vdata.redirect(null, "form");
    }
    
    /**
     * 
     * @param vdata
     * @throws Exception
     */
    public static final void insertcommon(ViewData vdata, ViewData selectview,
            Function function) throws Exception {
        Table table;
        DataForm form = (DataForm)vdata.getElement("model.form");
        ExtendedObject object = form.getObject();
        Documents documents = new Documents(function);
        
        if (documents.save(object) == 0) {
            vdata.message(Const.ERROR, "duplicated.entry");
            return;
        }
        
        table = (Table)selectview.getElement("selection_view");
        Common.addTableItem(table, object);
    }
    
    /**
     * 
     * @param vdata
     */
    public static final void insertnext(ViewData vdata, ViewData selectview,
            Function function) throws Exception {
        DataForm form = (DataForm)vdata.getElement("model.form");
        
        insertcommon(vdata, selectview, function);
        form.clearInputs();
        vdata.message(Const.STATUS, "insert.successful");
    }
    
    /**
     * 
     * @param vdata
     * @param function
     * @throws Exception
     */
    public static final void save(ViewData vdata, Function function)
            throws Exception {
        String value;
        InputComponent input;
        DocumentModelItem modelitem;
        ExtendedObject object;
        String modelname = (String)vdata.getParameter("model.name");
        Documents documents = new Documents(function);
        DocumentModel model = documents.getModel(modelname);
        Table table = ((Table)vdata.getElement("selection_view"));
        
        for (TableItem item : table.getItens()) {
            object = null;
            
            for (Element element: item.getElements()) {
                if (!element.isDataStorable())
                    continue;
                
                input = (InputComponent)element;
                modelitem = input.getModelItem();
                
                value = input.getValue();
                if (value == null && model.isKey(modelitem))
                    break;
                
                if (object == null)
                    object = new ExtendedObject(model);
                
                object.setValue(modelitem, input.getParsedValue());
            }
            
            if (object == null)
                continue;
            
            documents.modify(object);
            documents.commit();
        }
    }
    
    /**
     * 
     * @param vdata
     * @param function
     * @throws Exception
     */
    public static final void show(ViewData vdata, Function function)
            throws Exception {
        ExtendedObject[] itens;
        String modelname = ((InputComponent)vdata.
                getElement("model.name")).getValue();
        
        try {
            itens = getTableItens(modelname, function);
        } catch (Exception e) {
            vdata.message(Const.ERROR, e.getMessage());
            return;
        }
        
        vdata.clearParameters();
        vdata.addParameter("mode", "show");
        vdata.addParameter("view.type", Const.SINGLE);
        vdata.addParameter("model.name", modelname);
        vdata.export("model.regs", itens);
        vdata.setReloadableView(true);
        vdata.redirect(null, "select");
    }

}
