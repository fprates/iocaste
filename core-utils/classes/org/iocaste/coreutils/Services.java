package org.iocaste.coreutils;

import org.iocaste.coreutils.common.StandardDocument;
import org.iocaste.documents.common.Document;
import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Message;

public class Services extends AbstractFunction {
    
    public Services() {
        export("get_document_model", "getDocumentModel");
    }
    
    public final Document getDocumentModel(Message message) {
        return new StandardDocument();
    }
}
