package org.iocaste.dataeditor;

import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Const;
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
    public static final void insert(View view) {
        view.redirect("form");
    }
    
    public static final String load(String modelname, Context context) {
        String query;
        Documents documents = new Documents(context.function);
        
        context.model = documents.getModel(modelname);
        if (context.model == null)
            return "invalid.model";
        
        if (context.model.getTableName() == null)
            return "is.reference.model";
        
        context.viewtype = Const.SINGLE;
        
        query = new StringBuilder("from ").append(modelname).toString();
        context.itens = documents.select(query);
        
        return null;
    }
    
    /**
     * 
     * @param vdata
     * @param context
     */
    public static final void save(View vdata, Context context) {
        Object value;
        InputComponent input;
        DocumentModelItem modelitem;
        ExtendedObject object;
        Documents documents = new Documents(context.function);
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
                if (value == null && context.model.isKey(modelitem))
                    break;
                
                if (object == null)
                    object = new ExtendedObject(context.model);
                
                object.setValue(modelitem, value);
            }
            
            if (object == null)
                continue;
            
            documents.modify(object);
        }
        
        vdata.message(Const.STATUS, "entries.saved");
    }
}
