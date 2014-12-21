package org.iocaste.docmanager;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.iocaste.docmanager.common.AbstractManager;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Message;

public class Services extends AbstractFunction {
    
    public Services() {
        export("exists", "exists");
        export("get", "get");
        export("save", "save");
    }
    
    public final boolean exists(Message message) {
        Object code = message.get("code");
        String cmodelname = message.get("cmodel_name");
        Documents documents = new Documents(this);
        ComplexModel cmodel = documents.getComplexModel(cmodelname);
        String headmodelname = cmodel.getHeader().getName();
        ExtendedObject header = documents.getObject(headmodelname, code);
        
        return (header != null);
    }
    
    public final ComplexDocument get(Message message) {
        String cmodelname = message.get("cmodel_name");
        Object code = message.get("code");
        return new Documents(this).getComplexDocument(cmodelname, code);
    }
    
    public final ComplexDocument save(Message message) {
        Map<String, String> keys, references;
        Map<String, DocumentModel> models;
        DocumentModel model;
        DocumentModelItem headerkey;
        String itemkey, modelname, reference, charitemid, charid;
        long numid, numitemid;
        int i, keytype;
        Object id;
        String cmodelname = message.get("cmodel_name");
        ExtendedObject head = message.get("head");
        Collection<ExtendedObject[]> groups = message.get("groups");
        Documents documents = new Documents(this);
        ComplexModel cmodel = documents.getComplexModel(cmodelname);
        ComplexDocument document = new ComplexDocument(cmodel);
        
        /*
         * localizamos o código do documento
         */
        headerkey = null;
        model = cmodel.getHeader();
        headerkey = AbstractManager.getKey(model);
        if (headerkey == null)
            throw new RuntimeException("Header key undefined.");

        id = charid = null;
        numid = 0;
        keytype = headerkey.getDataElement().getType();
        switch (keytype) {
        case DataType.CHAR:
            charid = head.getst(headerkey.getName());
            break;
        default:
            numid = head.getl(headerkey.getName());
            break;
        }
        
        /*
         * localizamos chaves e referências para preencher
         * os índices do objetos do documento.
         */
        keys = new HashMap<>();
        references = new HashMap<>();
        models = cmodel.getItems();
        for (String name : models.keySet()) {
            model = models.get(name);
            modelname = model.getName();
            keys.put(modelname, AbstractManager.getKey(model).getName());
            references.put(modelname, AbstractManager.
                    getReference(model, headerkey).getName());
        }
        
        document.setHeader(head);
        for (ExtendedObject[] group : groups) {
            i = 0;
            if (group == null)
                continue;
            for (ExtendedObject item : group) {
                modelname = item.getModel().getName();
                itemkey = keys.get(modelname);
                reference = references.get(modelname);
                switch (keytype) {
                case DataType.CHAR:
                    charitemid = charid.concat(String.format("%02d", i));
                    item.set(itemkey, charitemid);
                    id = charid;
                    break;
                default:
                    numitemid = (numid * 100) + i;
                    item.set(itemkey, numitemid);
                    id = numid;
                    break;
                }
                
                i++;
                item.set(reference, id);
            }
            document.add(group);
        }

        documents.deleteComplexDocument(cmodelname, id);
        documents.save(document);
        
        return document;
    }
}
