package org.iocaste.sh;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;

public class SearchHelpRemove extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        Query query;
        ExtendedObject[] objects;
        Services function = getFunction();
        Documents documents = new Documents(function);
        String shname = message.getst("shname");
        
        query = new Query();
        query.setModel("SH_REFERENCE");
        query.setMaxResults(1);
        query.andEqual("SEARCH_HELP", shname);
        objects = documents.select(query);
        if (objects != null)
            throw new IocasteException("Search help has pendence on %s",
                    objects[0].getst("MODEL_ITEM"));
        
        query = new Query("delete");
        query.setModel("SH_QUERIES");
        query.andEqual("SEARCH_HELP", shname);
        documents.update(query);
        
        query = new Query("delete");
        query.setModel("SH_ITENS");
        query.andEqual("SEARCH_HELP", shname);
        documents.update(query);
        
        query = new Query("delete");
        query.setModel("SEARCH_HELP");
        query.andEqual("NAME", shname);
        documents.update(query);
        
        function.cache.remove(shname);
        return 1;
    }

}
