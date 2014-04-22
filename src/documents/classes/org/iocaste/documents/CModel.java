package org.iocaste.documents;

import java.util.Map;

import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;

public class CModel {
    
    /**
     * 
     * @param model
     * @param cache
     * @return
     * @throws Exception
     */
    public static final int create(ComplexModel cmodel, Cache cache)
            throws Exception {
        ExtendedObject object;
        Map<String, DocumentModel> items;
        DocumentModel model = Model.get("COMPLEX_MODEL", cache);
        String cmodelname = cmodel.getName();
        
        object = new ExtendedObject(model);
        object.set("NAME", cmodelname);
        object.set("MODEL", cmodel.getHeader().getName());
        Save.init(object, cache.function);
        
        model = Model.get("COMPLEX_MODEL_ITEM", cache);
        items = cmodel.getItems();
        for (String name : items.keySet()) {
            object = new ExtendedObject(model);
            object.set("IDENT", new StringBuilder(cmodelname).
                    append("_").
                    append(name).toString());
            object.set("NAME", name);
            object.set("CMODEL", cmodelname);
            object.set("MODEL", items.get(name).getName());
            Save.init(object, cache.function);
        }
        
        return 1;
    }
    
    /**
     * 
     * @param name
     * @param cache
     * @return
     * @throws Exception
     */
    public static final ComplexModel get(String name, Cache cache)
            throws Exception {
        ExtendedObject object;
        ExtendedObject[] objects;
        ComplexModel cmodel;
        Query query;
        
        object = Select.get(
                Model.get("COMPLEX_MODEL", cache), name, cache.function);
        if (object == null)
            return null;
        
        cmodel = new ComplexModel(name);
        cmodel.setHeader(Model.get(object.getst("MODEL"), cache));
        
        query = new Query();
        query.setModel("COMPLEX_MODEL_ITEM");
        query.andEqual("CMODEL", name);
        objects = Select.init(query, cache);
        if (objects == null)
            return cmodel;
        
        for (ExtendedObject item : objects)
            cmodel.put(
                    item.getst("NAME"),
                    Model.get(item.getst("MODEL"), cache));
        
        return cmodel;
    }
    
    /**
     * 
     * @param cmodel
     * @param cache
     * @return
     * @throws Exception
     */
    public static final int remove(ComplexModel cmodel, Cache cache)
            throws Exception {
        Query query;
        int error;
        String name = cmodel.getName();
        
        query = new Query("delete");
        query.setModel("COMPLEX_MODEL_ITEM");
        query.andEqual("CMODEL", name);
        Update.init(query, cache);
        
        query = new Query("delete");
        query.setModel("COMPLEX_MODEL");
        query.andEqual("NAME", name);
        error = Update.init(query, cache);
        if (error > 0)
            cache.cmodels.remove(name);
        
        return error;
    }
}
