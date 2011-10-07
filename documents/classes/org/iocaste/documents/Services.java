package org.iocaste.documents;

import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.range.NumericRange;
import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.HibernateUtil;
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
    public final DocumentModel getDocumentModel(Message message)
            throws Exception {
        DocumentModel document;
        Session session;
        Set<DocumentModelItem> itens;
        String documentname = message.getString("name");
        
        if (documentname == null)
            throw new Exception("Document model not specified.");

        session = HibernateUtil.getSessionFactory().getCurrentSession();
        document = (DocumentModel)session.get(
                DocumentModel.class, documentname);
        
        if (document == null)
            throw new Exception("Document not found.");
        
        Hibernate.initialize(document);
        itens = document.getItens();
        Hibernate.initialize(itens);
        
        for (DocumentModelItem item : itens)
            Hibernate.initialize(item.getDataElement());
        
        return document;
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

        session = HibernateUtil.getSessionFactory().getCurrentSession();
        range = (NumericRange)session.get(NumericRange.class, ident);
        
        if (range == null)
            throw new Exception("Range \""+ident+"\" not found.");
        
        range.setCurrent(range.getCurrent()+1);

        session.update(range);
        
        return range.getCurrent();
    }
}
