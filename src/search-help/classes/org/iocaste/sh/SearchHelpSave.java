package org.iocaste.sh;

import java.util.Map;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.SearchHelpColumn;
import org.iocaste.documents.common.SearchHelpData;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;

public class SearchHelpSave extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        String model, export, shname, exmessage;
        Documents documents;
        Services function;
        ExtendedObject object;
        Map<String, SearchHelpColumn> items;
        SearchHelpData shdata = message.get("shdata");
        
        items = shdata.getItems();
        if (items.size() == 0)
            throw new IocasteException("sh has no columns itens.");
        
        function = getFunction();
        documents = new Documents(function);
        model = shdata.getModel();
        if (documents.getModel(model) == null)
            throw new IocasteException(
                    String.format("%s is an invalid model.", model));

        shname = shdata.getName();
        export = function.composeName(model, shdata.getExport());
        
        object = new ExtendedObject(documents.getModel("SEARCH_HELP"));
        object.set("NAME", shname);
        object.set("MODEL", model);
        object.set("EXPORT", export);
        if (documents.save(object) == 0)
            throw new Exception (new StringBuilder("Error saving header of " +
                    "sh ").append(shname).toString());

        object = new ExtendedObject(documents.getModel("SH_ITENS"));
        for (String key : items.keySet()) {
            object.set("NAME", function.composeName(shname, key));
            object.set("ITEM", function.composeName(model, key));
            object.set("SEARCH_HELP", shname);
            
            if (documents.save(object) != 0)
                continue;
            
            exmessage = String.format(
                    "Error saving line of sh %s", object.getst("NAME"));
            throw new IocasteException(exmessage);
        }
        
        function.cache.put(shname, shdata);
        return null;
    }
    
}