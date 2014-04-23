package org.iocaste.docmanager.common;

import java.util.Collection;

import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Function;

public abstract class AbstractManager implements Manager {
    private DocumentManager docmanager;
    private String cmodelname;
    private String[] messages;
    
    public AbstractManager(String cmodel, Function function) {
        this.cmodelname = cmodel;
        docmanager = new DocumentManager(function);
    }
    
    /*
     * (não-Javadoc)
     * @see com.b2b.Manager#exists(java.lang.String)
     */
    @Override
    public final boolean exists(String code) {
        return docmanager.exists(cmodelname, code);
    }

    /*
     * (não-Javadoc)
     * @see com.b2b.Manager#get(java.lang.String)
     */
    @Override
    public final ComplexDocument get(String code) {
        return docmanager.get(cmodelname, code);
    }

    public static final DocumentModelItem getKey(DocumentModel model) {
        String name;
        
        for (DocumentModelKey key : model.getKeys()) {
            name = key.getModelItemName();
            return model.getModelItem(name);
        }
        
        return null;
    }
    
    /*
     * (não-Javadoc)
     * @see com.b2b.Manager#getMessage(int)
     */
    @Override
    public final String getMessage(int messageid) {
        return (messages == null)? null : messages[messageid];
    }
    
    public static final DocumentModelItem getReference(DocumentModel model,
            DocumentModelItem key) {
        DocumentModelItem reference;
        
        for (DocumentModelItem item : model.getItens()) {
            reference = item.getReference();
            if (reference == null || !key.equals(reference))
                continue;
            return item;
        }
        return null;
    }
    
    /*
     * (não-Javadoc)
     * @see com.b2b.Manager#save(org.iocaste.documents.common.ComplexDocument)
     */
    @Override
    public final void save(
            ExtendedObject head, Collection<ExtendedObject[]> groups) {
        docmanager.save(cmodelname, head, groups);
    }
    
    protected final void setMessages(String[] messages) {
        this.messages = messages;
    }

}
