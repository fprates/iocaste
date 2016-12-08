package org.iocaste.sh;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.documents.common.SearchHelpData;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;

public class SearchHelpUpdate extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        Query query;
        ExtendedObject object;
        String model, export, shname;
        SearchHelpData shdata = message.get("shdata");
        Services function = getFunction();
        Documents documents = new Documents(function);
        
        shname = shdata.getName();
        model = shdata.getModel();
        export = function.composeName(model, shdata.getExport());
        
        object = new ExtendedObject(documents.getModel("SEARCH_HELP"));
        object.set("NAME", shname);
        object.set("MODEL", model);
        object.set("EXPORT", export);
        documents.modify(object);
        
        query = new Query("delete");
        query.setModel("SH_ITENS");
        query.andEqual("SEARCH_HELP", shname);
        documents.update(query);
        
        object = new ExtendedObject(documents.getModel("SH_ITENS"));
        for (String key : shdata.getItems().keySet()) {
            object.set("NAME", function.composeName(shname, key));
            object.set("ITEM", function.composeName(model, key));
            object.set("SEARCH_HELP", shname);
            documents.save(object);
        }
        
        function.cache.put(shname, shdata);
        return null;
    }

}
