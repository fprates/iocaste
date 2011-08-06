package org.iocaste.coreutils;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Message;

public class Services extends AbstractFunction {
    
    public Services() {
        export("get_document_model", "getDocumentModel");
    }
    
    public final DocumentModel getDocumentModel(Message message) throws Exception {
        String documentname = message.getString("name");
        
        if (documentname == null)
            throw new Exception("Document model not especified.");
        
        return (DocumentModel)load(DocumentModel.class, documentname);
    }
}
