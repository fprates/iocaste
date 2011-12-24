package org.iocaste.documents;

import java.util.Map;


import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.Message;

public class Services extends AbstractFunction {
    private DocumentServices doc;
    
    public Services() {
        doc = new DocumentServices(this);
        
        export("get_data_element", "getDataElement");
        export("get_next_number", "getNextNumber");
        export("get_document_model", "getDocumentModel");
        export("has_model", "hasModel");
        export("create_model", "createModel");
        export("remove_model", "removeModel");
    }
    
    /**
     * 
     * @param message
     * @throws Exception
     */
    public final void createModel(Message message) throws Exception {
        DocumentModel model = (DocumentModel)message.get("model");
        
        doc.createModel(model);
    }
    
    /**
     * 
     * @param message
     * @return
     * @throws Exception 
     */
    public final DataElement getDataElement(Message message) throws Exception {
        String name = message.getString("name");
        
        return doc.getDataElement(new Iocaste(this), name);
    }
    
    /**
     * Retorna modelo de documento especificado.
     * @param mensagem
     * @return modelo de documento
     * @throws Exception
     */
    public final DocumentModel getDocumentModel(Message message)
            throws Exception {
        String documentname = message.getString("name");
        
        return doc.getDocumentModel(documentname);
    }
    
    /**
     * Retorna próximo número do range
     * @param messagem
     * @return próximo número
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public final long getNextNumber(Message message) throws Exception {
        long current;
        Object[] lines;
        Map<String, Object> columns;
        String ident = message.getString("range");
        Iocaste iocaste = new Iocaste(this);
        
        if (ident == null)
            throw new Exception("Numeric range not specified.");

        lines = iocaste.select("select crrnt from range001 where ident = ?",
                new Object[] {ident});
        
        if (lines.length == 0)
            throw new Exception("Range \""+ident+"\" not found.");
        
        columns = (Map<String, Object>)lines[0];
        current = ((Long)columns.get("CRRNT"))+1;
        iocaste.update("update range001 set crrnt = ? where ident = ?",
                new Object[] {current, ident});
        iocaste.commit();
        
        return current;
    }
    
    /**
     * 
     * @param message
     * @return
     * @throws Exception
     */
    public final boolean hasModel(Message message) throws Exception {
        String modelname = message.getString("model_name");

        return doc.hasModel(modelname);
    }
    
    /**
     * 
     * @param message
     * @throws Exception
     */
    public final void removeModel(Message message) throws Exception {
        String modelname = message.getString("model_name");
        DocumentModel model = doc.getDocumentModel(modelname);
        
        doc.removeModel(model);
    }
}
