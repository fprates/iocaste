package org.iocaste.packagetool;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;

public class InstallCModels {
    
    /**
     * 
     * @param cmodels
     * @param state
     * @throws Exception
     */
    public static final void init(ComplexModel[] cmodels, State state)
            throws Exception {
        String name;
        DocumentModel model, dmodel;
        DocumentModelItem item;
        List<DocumentModel> models = new ArrayList<DocumentModel>();
        DocumentModelItem ditem, cdocid = state.documents.
                getModel("COMPLEX_DOCUMENT").getModelItem("ID");
        DataElement element = cdocid.getDataElement();
        
        for (ComplexModel cmodel : cmodels) {
            state.documents.create(cmodel);
            Registry.add(cmodel.getName(), "CMODEL", state);
            
            model = new DocumentModel();
            model.setName("CMI_"+cmodel.getName());
            dmodel = cmodel.getHeader();
            model.setTableName("CMI_"+dmodel.getTableName());
            
            item = new DocumentModelItem();
            item.setName("ID");
            item.setTableFieldName("IDENT");
            item.setDataElement(element);
            item.setReference(cdocid);
            model.add(item);
            model.add(new DocumentModelKey(item));
            
            for (DocumentModelKey key : dmodel.getKeys()) {
                name = key.getModelItemName();
                ditem = dmodel.getModelItem(name);
                item = new DocumentModelItem();
                item.setName("Z"+name);
                item.setTableFieldName("Z"+ditem.getTableFieldName());
                item.setDataElement(ditem.getDataElement());
                item.setReference(ditem);
                model.add(item);
            }
            
            models.add(model);
        }
        
        InstallModels.init(models.toArray(new DocumentModel[0]), state);
    }
}
