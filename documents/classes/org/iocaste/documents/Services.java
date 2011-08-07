package org.iocaste.documents;

import org.hibernate.Session;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.range.NumericRange;
import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Message;

public class Services extends AbstractFunction {
    
    public Services() {
        export("get_next_number", "getNextNumber");
        export("get_document_model", "getDocumentModel");
    }
    
    /**
     * 
     * @param message
     * @return
     * @throws Exception
     */
    public final DocumentModel getDocumentModel(Message message) throws Exception {
        String documentname = message.getString("name");
        
        if (documentname == null)
            throw new Exception("Document model not specified.");
        
        return (DocumentModel)load(DocumentModel.class, documentname);
    }
    
    /**
     * 
     * @param message
     * @return
     * @throws Exception
     */
    public final long getNextNumber(Message message) throws Exception {
        Session session;
        NumericRange range;
        String ident = message.getString("range");
        
        if (ident == null)
            throw new Exception("Numeric range not specified.");
        
        range = (NumericRange)load(NumericRange.class, ident);
        
        if (range == null)
            throw new Exception("Range \""+ident+"\" not found.");
        
        range.setCurrent(range.getCurrent()+1);

        session = getHibernateSession();
        session.update(range);
        
        return range.getCurrent();
    }
}
