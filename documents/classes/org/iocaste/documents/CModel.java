package org.iocaste.documents;

import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.IocasteException;

public class CModel {
    private static final byte DEL_CMODEL_ITENS = 0;
    private static final byte DEL_CMODEL = 1;
    private static final byte CMODEL_ITENS = 2;
    private static final String[] QUERIES = {
        "delete from COMPLEX_MODEL_ITEM where COMPLEX_MODEL = ?",
        "delete from COMPLEX_MODEL where NAME = ?",
        "select * from COMPLEX_MODEL_ITEM where COMPLEX_MODEL = ?"
    };
    
    private static final byte ITEM_SAVE = 0;
    private static final String[] ERRORS = {
        "error on complex model item saving"
    };
    
    /**
     * 
     * @param model
     * @param cache
     * @return
     * @throws Exception
     */
    public static final int create(ComplexModel model, Cache cache)
            throws Exception {
        ExtendedObject item;
        long cmodelid;
        DocumentModel hmodel = Model.get("COMPLEX_MODEL", cache);
        DocumentModel imodel = Model.get("COMPLEX_MODEL_ITEM", cache);
        ExtendedObject header = new ExtendedObject(hmodel);
        String name = model.getName();
        
        header.setValue("NAME", name);
        cmodelid = NumberRange.getCurrent("CMODEL_ID", cache.function);
        header.setValue("ID", cmodelid);
        header.setValue("MODEL", model.getHeader().getName());
        Query.save(header, cache.function);
        cmodelid *= 1000;
        for (DocumentModel modelitem : model.getItens()) {
            item = new ExtendedObject(imodel);
            item.setValue("ID", cmodelid++);
            item.setValue("COMPLEX_MODEL", name);
            item.setValue("MODEL", modelitem.getName());
            if (Query.save(item, cache.function) == 0)
                throw new IocasteException(ERRORS[ITEM_SAVE]);
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
        ComplexModel cmodel;
        DocumentModel model;
        ExtendedObject omodel;
        String modelname;
        ExtendedObject[] itens;
        
        if (cache.cmodels.containsKey(name))
            return cache.cmodels.get(name);
        
        model = Model.get("COMPLEX_MODEL", cache);
        omodel = Query.get(model, name, cache.function);
        if (omodel == null)
            return null;
        
        modelname = omodel.getValue("MODEL");
        cmodel = new ComplexModel();
        cmodel.setName(name);
        cmodel.setHeader(Model.get(modelname, cache));
        cache.cmodels.put(name, cmodel);
        itens = Query.select(QUERIES[CMODEL_ITENS], 0, cache, name);
        if (itens == null)
            return cmodel;
        
        for (ExtendedObject item : itens) {
            modelname = item.getValue("MODEL");
            model = Model.get(modelname, cache);
            cmodel.add(model);
        }
        
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
        int error;
        String name = cmodel.getName();
        
        Query.update(QUERIES[DEL_CMODEL_ITENS], cache, name);
        error = Query.update(QUERIES[DEL_CMODEL], cache, name);
        if (error > 0)
            cache.cmodels.remove(name);
        
        return error;
    }
}
