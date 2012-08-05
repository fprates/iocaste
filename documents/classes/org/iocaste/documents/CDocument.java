package org.iocaste.documents;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.IocasteException;

public class CDocument {
    private static final long HMULTIPLIER = 100000000;
    private static final int IMULTIPLIER = 100000;
    private static final byte COMPLEX_MODEL = 0;
    private static final byte COMPLEX_DOCUMENT = 1;
    private static final byte LINK = 2;
    
    /**
     * 
     * @param cdname
     * @param id
     * @param cache
     * @return
     * @throws Exception
     */
    public static final ComplexDocument get(String cdname, long id, Cache cache)
            throws Exception {
        StringBuilder sb;
        ExtendedObject[] objects;
        ComplexDocument document;
        ExtendedObject header;
        DocumentModel hmodel;
        DocumentModelItem ireference, reference = null;
        long key = 0;
        
        document = getHeader(cdname, id, cache);
        if (document == null)
            return null;
        
        header = document.getHeader();
        hmodel = header.getModel();
        for (DocumentModelKey modelkey : hmodel.getKeys()) {
            reference = hmodel.getModelItem(modelkey.getModelItemName());
            key = (Long)header.getValue(reference);
            break;
        }
        
        for (DocumentModel model : document.getModel().getItens()) {
            sb = new StringBuilder("from ").
                    append(model.getName()).
                    append(" where ");
            
            for (DocumentModelItem modelitem : model.getItens()) {
                ireference = modelitem.getReference();
                if (model.isKey(modelitem) || ireference == null)
                    continue;
                
                if (!ireference.equals(reference))
                    continue;
                
                sb.append(modelitem.getName());
                break;
            }
            
            sb.append(" = ?");
            objects = Query.select(sb.toString(), 0, cache, key);
            if (objects == null)
                continue;
            
            for (ExtendedObject object  : objects)
                document.add(object);
        }
        
        return document;
    }
    
    private static final ComplexDocument getHeader(String name, long id,
            Cache cache) throws Exception {
        long key;
        ComplexDocument document;
        DocumentModel modellink;
        ComplexModel cmmodel = CModel.get(name, cache);
        long cdocid = (cmmodel.getId() * HMULTIPLIER) + id;
        DocumentModel model = Model.get("COMPLEX_DOCUMENT", cache);
        ExtendedObject object = Query.get(model, cdocid, cache.function);
        
        if (object == null)
            return null;
        
        key = object.getValue("ID");
        document = new ComplexDocument(cmmodel);
        document.setId(key);
        modellink = cmmodel.getCDModelLink();
        object = Query.get(modellink, key, cache.function);
        key = 0;
        for (DocumentModelItem item : modellink.getItens()) {
            if (modellink.isKey(item))
                continue;
            key = object.getValue(item);
            break;
        }
        
        object = Query.get(cmmodel.getHeader(), key, cache.function);
        document.setHeader(object);
        
        return document;
    }
    
    /**
     * 
     * @param document
     * @param cache
     * @return
     * @throws Exception
     */
    public static final long save(ComplexDocument document, Cache cache)
            throws Exception {
        long current;
        ExtendedObject[] objects = new ExtendedObject[3];
        
        saveCDocument(document, objects, cache);
        current = saveModelLink(objects, cache);
        saveDocument(objects, document, cache);
        
        return current;
    }
    
    private static final void saveCDocument(ComplexDocument document,
            ExtendedObject[] objects, Cache cache) throws Exception {
        String username;
        Date date;
        int cmodelid;
        long id, current;
        ExtendedObject[] itens;
        ExtendedObject docitem;
        ComplexModel cmodel = document.getModel();
        String cmodelname = cmodel.getName();
        DocumentModel model = Model.get("COMPLEX_MODEL", cache);
        
        objects[COMPLEX_MODEL] = Query.get(model, cmodelname, cache.function);
        cmodelid = objects[COMPLEX_MODEL].getValue("ID");
        current = objects[COMPLEX_MODEL].getValue("CURRENT");
        
        if (current == 0)
            current = cmodelid * HMULTIPLIER;
        
        current++;
        objects[0].setValue("CURRENT", current);
        if (Query.modify(objects[COMPLEX_MODEL], cache.function) == 0)
            throw new IocasteException("error on complex model update");

        model = Model.get("COMPLEX_DOCUMENT", cache);
        objects[COMPLEX_DOCUMENT] = new ExtendedObject(model);
        objects[COMPLEX_DOCUMENT].setValue("ID", current);
        objects[COMPLEX_DOCUMENT].setValue("COMPLEX_MODEL", cmodel.getName());
        date = Calendar.getInstance().getTime();
        username = new Iocaste(cache.function).getUsername();
        objects[COMPLEX_DOCUMENT].setValue("DATA_CRIACAO", date);
        objects[COMPLEX_DOCUMENT].setValue("HORA_CRIACAO", date);
        objects[COMPLEX_DOCUMENT].setValue("USUARIO_CRIACAO", username);
        if (Query.save(objects[COMPLEX_DOCUMENT], cache.function) == 0)
            throw new IocasteException("error on insert complex document");
        
        model = Model.get("COMPLEX_DOCUMENT_ITEM", cache);
        id = current * IMULTIPLIER;
        for (String modelname : document.getItensModels()) {
            itens = document.getItens(modelname);
            for (ExtendedObject item : itens) {
                docitem = new ExtendedObject(model);
                id++;
                docitem.setValue("ID", id);
                docitem.setValue("COMPLEX_DOCUMENT", current);
                Query.save(docitem, cache.function);
            }
        }
    }
    
    private static final void saveDocument(ExtendedObject[] objects,
            ComplexDocument document, Cache cache) {
        String zname, name;
        long t;
        Object hkeyvalue = null;
        DocumentModelItem ikey, hkey = null;
        ExtendedObject header = document.getHeader();
        DocumentModel model = objects[LINK].getModel();
        
        for (DocumentModelItem item : model.getItens()) {
            if (model.isKey(item))
                continue;
            
            zname = item.getName();
            name = zname.substring(1);
            hkeyvalue = objects[LINK].getValue(zname);
            header.setValue(name, hkeyvalue);
            hkey = header.getModel().getModelItem(name);
            break;
        }
        Query.save(header, cache.function);
        
        for (String modelname : document.getItensModels()) {
            model = document.getItemModel(modelname);
            t = 0;
            ikey = null;
            for (DocumentModelKey key : model.getKeys()) {
                ikey = model.getModelItem(key.getModelItemName());
                t = ikey.getDataElement().getLength() -
                        hkey.getDataElement().getLength();
                t = new BigDecimal(((Long)hkeyvalue) * Math.pow(10, t)).
                        longValue();
                break;
            }
            
            for (ExtendedObject object : document.getItens(modelname)) {
                setItemHeaderReference(object, hkey, hkeyvalue);
                object.setValue(ikey, t + (Long)object.getValue(ikey));
                Query.save(object, cache.function);
            }
        }
    }
    
    private static final long saveModelLink(ExtendedObject[] objects,
            Cache cache) throws Exception {
        long current = objects[COMPLEX_MODEL].getValue("CURRENT");
        int cmodelid = objects[COMPLEX_MODEL].getValue("ID");
        String modellinkname = objects[COMPLEX_MODEL].getValue("CD_LINK");
        DocumentModel modellink = Model.get(modellinkname, cache);
        
        objects[LINK] = new ExtendedObject(modellink);
        for (DocumentModelItem item : modellink.getItens()) {
            if (modellink.isKey(item)) {
                objects[LINK].setValue(item, current);
                continue;
            }
            current -= (cmodelid * HMULTIPLIER);
            objects[LINK].setValue(item, current);
            break;
        }
        
        if (Query.save(objects[LINK], cache.function) == 0)
            throw new IocasteException("error on insert complex document link");
        
        return current;
    }
    
    private static final void setItemHeaderReference(ExtendedObject object,
            DocumentModelItem hkey, Object value) {
        String name;
        DocumentModelItem reference;
        DocumentModel model = object.getModel();
        
        for (DocumentModelItem item : model.getItens()) {
            reference = item.getReference();
            if (model.isKey(item) || reference == null)
                continue;
            
            name = reference.getName();
            if (!name.equals(hkey.getName()))
                continue;
            
            object.setValue(item, value);
            break;
        }
    }
    
    public static final int update(ComplexDocument document, Cache cache)
            throws Exception {
        ExtendedObject[] objects;
        StringBuilder sb;
        DocumentModelItem iref, href = null;
        Object key = null;
        ExtendedObject header = document.getHeader();
        DocumentModel model = header.getModel();
        
        Query.modify(header, cache.function);
        for (DocumentModelKey modelkey : model.getKeys()) {
            href = model.getModelItem(modelkey.getModelItemName());
            key = header.getValue(href);
            break;
        }
        
        for (String modelname : document.getItensModels()) {
            model = document.getItemModel(modelname);
            iref = null;
            for (DocumentModelItem modelitem : model.getItens()) {
                if (model.isKey(modelitem))
                    continue;
                iref = modelitem.getReference();
                if (iref == null || !iref.getName().equals(href.getName()))
                    continue;
                iref = modelitem;
                break;
            }
            
            sb = new StringBuilder("delete from ").
                    append(modelname).
                    append(" where ").
                    append(iref.getName()).
                    append(" = ?");
            Query.update(sb.toString(), cache, key);
            
            objects = document.getItens(modelname);
            for (ExtendedObject object : objects) {
                object.setValue(iref, key);
                Query.save(object, cache.function);
            }
        }
        
        return 1;
    }
}
