package org.iocaste.documents;

import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
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
        DocumentModel cmlink;
        DocumentModelItem reference, cmlinkitem;
        long cmodelid;
        DocumentModel hmodel = Model.get("COMPLEX_MODEL", cache);
        DocumentModel imodel = Model.get("COMPLEX_MODEL_ITEM", cache);
        ExtendedObject object = new ExtendedObject(hmodel);
        String cminame, name = model.getName();
        
        object.setValue("NAME", name);
        cmodelid = NumberRange.getCurrent("CMODEL_ID", cache.function);
        object.setValue("ID", cmodelid);
        hmodel = model.getHeader();
        object.setValue("MODEL", hmodel.getName());
        cminame = new StringBuilder("CMI_").append(name).toString();
        object.setValue("CD_LINK", cminame);
        Query.save(object, cache.function);
        
        cmodelid *= 1000;
        for (DocumentModel modelitem : model.getItens()) {
            object = new ExtendedObject(imodel);
            object.setValue("ID", cmodelid++);
            object.setValue("COMPLEX_MODEL", name);
            object.setValue("MODEL", modelitem.getName());
            if (Query.save(object, cache.function) == 0)
                throw new IocasteException(ERRORS[ITEM_SAVE]);
        }
        
        cmlink = new DocumentModel();
        cmlink.setName(cminame);
        cmlink.setTableName("CMI_"+hmodel.getTableName());
        
        reference = hmodel.getModelItem("ID");
        cmlinkitem = new DocumentModelItem();
        cmlinkitem.setName("ID");
        cmlinkitem.setTableFieldName("IDENT");
        cmlinkitem.setDataElement(reference.getDataElement());
        cmlinkitem.setReference(reference);
        cmlink.add(cmlinkitem);
        cmlink.add(new DocumentModelKey(cmlinkitem));
        
        for (DocumentModelKey key : hmodel.getKeys()) {
            name = key.getModelItemName();
            reference = hmodel.getModelItem(name);
            cmlinkitem = new DocumentModelItem();
            cmlinkitem.setName("Z"+name);
            cmlinkitem.setTableFieldName("Z"+reference.getTableFieldName());
            cmlinkitem.setDataElement(reference.getDataElement());
            cmlinkitem.setReference(reference);
            cmlink.add(cmlinkitem);
        }
        
        Model.create(cmlink, cache);
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
