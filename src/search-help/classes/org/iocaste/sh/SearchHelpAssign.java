package org.iocaste.sh;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;

public class SearchHelpAssign extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        DocumentModelItem item = message.get("model_item");
        Documents documents = new Documents(getFunction());
        DocumentModel shref = documents.getModel("SH_REFERENCE");
        ExtendedObject reference = new ExtendedObject(shref);
        
        reference.set("MODEL_ITEM", item.getIndex());
        reference.set("SEARCH_HELP", item.getSearchHelp());
        
        return documents.save(reference);
    }
    
}