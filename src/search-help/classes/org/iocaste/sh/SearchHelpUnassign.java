package org.iocaste.sh;

import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.Query;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;

public class SearchHelpUnassign extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        DocumentModelItem item = message.get("item");
        Query query = new Query("delete");
        
        query.setModel("SH_REFERENCE");
        query.andEqual("MODEL_ITEM", item.getIndex());
        return new Documents(getFunction()).update(query);
    }
    
}
