package org.iocaste.sh;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.documents.common.SearchHelpData;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;

public class SearchHelpLoad extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        return run(message.getst("name"));
    }
    
    public final SearchHelpData run(String name) throws Exception {
        Query query;
        Documents documents;
        ExtendedObject header;
        ExtendedObject[] items;
        SearchHelpData shdata;
        Services function = getFunction();
        
        if (function.cache.containsKey(name))
            return function.cache.get(name);
        
        documents = new Documents(function);
        header = documents.getObject("SEARCH_HELP", name);
        if (header == null)
            return null;
        
        query = new Query();
        query.setModel("SH_ITENS");
        query.andEqual("SEARCH_HELP", name);
        items = documents.select(query);
        if (items == null)
            throw new IocasteException("sh has no columns itens.");
        
        shdata = new SearchHelpData(name);
        shdata.setModel(header.getst("MODEL"));
        shdata.setExport(header.getst("EXPORT"));
        
        for (ExtendedObject item : items)
            shdata.add(item.get("ITEM"));
        
        function.cache.put(name, shdata);
        
        return shdata;
    }

}
