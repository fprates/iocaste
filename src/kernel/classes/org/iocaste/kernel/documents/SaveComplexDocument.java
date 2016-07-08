package org.iocaste.kernel.documents;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.ComplexDocumentItems;
import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.ComplexModelItem;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Message;

public class SaveComplexDocument extends AbstractDocumentsHandler {

    public Object run(Message message) throws Exception {
        Map<String, String> keys, references;
        Map<String, ComplexModelItem> models;
        ComplexModelItem cmodelitem;
        DocumentModel model;
        DocumentModelItem headerkey;
        String itemkey, modelname, reference, charitemid, charid;
        long numid, numitemid;
        int i, keytype;
        Object id, ns;
        ComplexModel cmodel;
        ExtendedObject head;
        ComplexDocumentItems group;
        ExtendedObject item;
        Map<String, ComplexModelItem> cmodelitems;
        ComplexDocument document = message.get("document");
        
        /*
         * localizamos o código do documento
         */
        headerkey = null;
        cmodel = document.getModel();
        model = cmodel.getHeader();
        headerkey = org.iocaste.documents.common.Documents.getKey(model);
        if (headerkey == null)
            throw new RuntimeException("Header key undefined.");

        id = charid = null;
        numid = 0;
        keytype = headerkey.getDataElement().getType();
        head = document.getHeader();
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
            cmodelitem = models.get(name);
            model = cmodelitem.model;
            if (model == null)
                continue;
            modelname = model.getName();
            keys.put(modelname, org.iocaste.documents.common.Documents.
                    getKey(model).getName());
            references.put(modelname, org.iocaste.documents.common.Documents.
                    getReference(model, headerkey).getName());
        }
        
        ns = document.getNS();
        cmodelitems = cmodel.getItems();
        for (String key : cmodelitems.keySet()) {
            group = document.get(key);
            i = 0;
            if ((group == null) || (group.objects == null))
                continue;
            cmodelitem = cmodelitems.get(key);
            for (Object groupitemkey : group.objects.keySet()) {
                item = group.objects.get(groupitemkey);
                modelname = item.getModel().getName();
                itemkey = keys.get(modelname);
                reference = references.get(modelname);
                switch (keytype) {
                case DataType.CHAR:
                    charitemid = charid.concat(
                            String.format(cmodelitem.keyformat, i));
                    item.set(itemkey, charitemid);
                    id = charid;
                    break;
                default:
                    numitemid = cmodelitem.keydigits;
                    numitemid = (numid * (int)(Math.pow(10, numitemid))) + i;
                    item.set(itemkey, numitemid);
                    id = numid;
                    break;
                }
                
                i++;
                item.setNS(ns);
                item.set(reference, id);
            }
        }

        save(document, message.getSessionid());
        return document;
    }

    private final void save(ComplexDocument document, String sessionid) throws Exception {
        DeleteDocument delete;
        ModifyDocument modify;
        GetComplexDocument getcdoc;
        Map<String, ComplexModelItem> models;
        ExtendedObject[] nobjects;
        ComplexDocument original;
        Map<Object, ExtendedObject> items;
        ComplexModelItem cmodelitem;
        ComplexModel cmodel = document.getModel();
        CDocumentData data = new CDocumentData();

        data.cdname = cmodel.getName();
        data.id = document.getKey();
        data.ns = document.getNS();
        data.documents = getFunction();
        data.sessionid = sessionid;
        data.connection = data.documents.database.
                getDBConnection(data.sessionid);
        
        modify = data.documents.get("modify");
        modify.run(data.documents, data.connection, document.getHeader());
        models = cmodel.getItems();
        for (String name : models.keySet()) {
            cmodelitem = models.get(name);
            if (cmodelitem.model == null)
                continue;
            nobjects = document.getItems(name);
            for (ExtendedObject item : nobjects)
                modify.run(data.documents, data.connection, item);
        }

        getcdoc = data.documents.get("get_complex_document");
        original = getcdoc.run(data);
        if (original == null)
            return;
        
        delete = data.documents.get("delete_document");
        for (String name : models.keySet()) {
            cmodelitem = models.get(name);
            if (cmodelitem.model == null)
                continue;
            items = original.getItemsMap(name);
            if (items == null)
                continue;
            for (Object key : items.keySet())
                if (!document.getItemsMap(name).containsKey(key))
                    delete.run(data.documents, data.connection, items.get(key));
        }
    }
}
