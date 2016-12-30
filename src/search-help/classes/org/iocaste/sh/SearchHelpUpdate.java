package org.iocaste.sh;

import org.iocaste.documents.common.DocumentModel;
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
        SearchHelpSave save;
        ExtendedObject object;
        DocumentModel model;
        String export, shname;
        SearchHelpData shdata = message.get("shdata");
        Services function = getFunction();
        Documents documents = new Documents(function);
        
        shname = shdata.getName();
        model = documents.getModel(shdata.getModel());
        export = model.getModelItem(shdata.getExport()).getIndex();
        
        object = new ExtendedObject(documents.getModel("SEARCH_HELP"));
        object.set("NAME", shname);
        object.set("MODEL", model);
        object.set("EXPORT", export);
        documents.modify(object);
        
        query = new Query("delete");
        query.setModel("SH_ITENS");
        query.andEqual("SEARCH_HELP", shname);
        documents.update(query);

        save = function.get("save");
        save.items(documents, shdata, model);
        
        query = new Query("delete");
        query.setModel("SH_QUERY");
        query.andEqual("SEARCH_HELP", shname);
        documents.update(query);
        
        save.queries(documents, model, shdata);
        
        function.cache.put(shname, shdata);
        return null;
    }

}
