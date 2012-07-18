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
import org.iocaste.shell.common.View;

public class Request {
    
    /**
     * 
     * @param vdata
     * @param function
     */
    public static final void delete(View vdata, Function function) {
        Table table = vdata.getElement("selection_view");
        Documents documents = new Documents(function);
        
        for (TableItem item : table.getItens()) {
            if (!item.isSelected())
                continue;
            
            if (documents.delete(item.getObject()) == 0) {
                vdata.message(Const.ERROR, "error.on.delete");
                return;
            }
            
            table.remove(item);
        }
        
        vdata.message(Const.STATUS, "delete.sucessful");
    }
    
    /**
     * 
     * @param vdata
     */
    public static final void insert(View vdata) {
        DocumentModel model = vdata.getParameter("model");
        
        vdata.clearExports();
        vdata.export("model", model);
        vdata.setReloadableView(true);
        vdata.redirect("form");
    }
    
    /**
     * 
     * @param vdata
     */
    public static final void insertcommon(View vdata, View selectview,
            Function function) {
        Table table;
        DataForm form = vdata.getElement("model.form");
        ExtendedObject object = form.getObject();
        Documents documents = new Documents(function);
        
        if (documents.save(object) == 0) {
            vdata.message(Const.ERROR, "duplicated.entry");
            return;
        }
        
        table = selectview.getElement("selection_view");
        Common.addTableItem(table, object);
    }
    
    /**
     * 
     * @param vdata
     */
    public static final void insertnext(View vdata, View selectview,
            Function function) {
        DataForm form = vdata.getElement("model.form");
        
        insertcommon(vdata, selectview, function);
        form.clearInputs();
        vdata.message(Const.STATUS, "insert.successful");
    }
    
    public static final void load(View view, Function function, byte mode) {
        ExtendedObject[] itens;
        InputComponent input = ((DataForm)view.getElement("model")).
                get("model.name");
        String query, modelname = input.get();
        Documents documents = new Documents(function);
        DocumentModel model = documents.getModel(modelname);
        
        if (model == null) {
            view.message(Const.ERROR, "invalid.model");
            return;
        }
        
        query = new StringBuilder("from ").append(modelname).toString();
        itens = documents.select(query);
        
        view.clearExports();
        view.export("mode", mode);
        view.export("view.type", Const.SINGLE);
        view.export("model", model);
        view.export("model.regs", itens);
        view.setReloadableView(true);
        view.redirect("select");
        
    }
    
    /**
     * 
     * @param vdata
     * @param function
     */
    public static final void save(View vdata, Function function) {
        Object value;
        InputComponent input;
        DocumentModelItem modelitem;
        ExtendedObject object;
        DocumentModel model = vdata.getParameter("model");
        Documents documents = new Documents(function);
        Table table = vdata.getElement("selection_view");
        
        for (TableItem item : table.getItens()) {
            object = null;
            
            for (Element element: item.getElements()) {
                if (!element.isDataStorable())
                    continue;
                
                input = (InputComponent)element;
                modelitem = input.getModelItem();
                if (modelitem == null)
                    continue;
                
                value = input.get();
                if (value == null && model.isKey(modelitem))
                    break;
                
                if (object == null)
                    object = new ExtendedObject(model);
                
                object.setValue(modelitem, value);
            }
            
            if (object == null)
                continue;
            
            documents.modify(object);
        }
        
        vdata.message(Const.STATUS, "entries.saved");
    }
}
