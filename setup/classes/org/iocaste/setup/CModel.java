package org.iocaste.setup;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.documents.common.Documents;
import org.iocaste.packagetool.common.InstallData;

public class CModel {
    
    /**
     * 
     * @param data
     * @param config
     */
    public static final void install(InstallData data, Config config) {
        DocumentModel model;
        DataElement element;
        DocumentModelItem modelname, item;
        
        /*
         * Modelo de documento complexo
         */
        model = data.getModel("COMPLEX_MODEL", "CPLXMODEL", null);
        
        // identificador
        element = new DataElement();
        element.setName("COMPLEX_MODEL.NAME");
        element.setType(DataType.CHAR);
        element.setUpcase(true);
        element.setLength(24);
        
        config.cmodelname = new DocumentModelItem();
        config.cmodelname.setName("NAME");
        config.cmodelname.setTableFieldName("IDENT");
        config.cmodelname.setDataElement(element);
        model.add(config.cmodelname);
        model.add(new DocumentModelKey(config.cmodelname));

        // índice
        element = new DataElement();
        element.setName("COMPLEX_MODEL.ID");
        element.setType(DataType.NUMC);
        element.setLength(3);
        
        item = new DocumentModelItem();
        item.setName("ID");
        item.setTableFieldName("MDLID");
        item.setDataElement(element);
        model.add(item);
        
        // modelo
        modelname = new Documents(config.function).getModel("MODEL").
                getModelItem("NAME");
        element = modelname.getDataElement();
        
        item = new DocumentModelItem();
        item.setName("MODEL");
        item.setTableFieldName("MODEL");
        item.setDataElement(element);
        item.setReference(modelname);
        model.add(item);
        
        // último documento para este modelo
        element = new DataElement();
        element.setName("COMPLEX_DOCUMENT.ID");
        element.setType(DataType.NUMC);
        element.setLength(11);
        
        config.cdocumentid = new DocumentModelItem();
        config.cdocumentid.setName("CURRENT");
        config.cdocumentid.setTableFieldName("DOCID");
        config.cdocumentid.setDataElement(element);
        model.add(config.cdocumentid);
        
        // link documento-document complexo
        element = new DataElement();
        element.setName("COMPLEX_MODEL.CD_LINK");
        element.setType(DataType.CHAR);
        element.setLength(24);
        element.setUpcase(true);
        
        item = new DocumentModelItem();
        item.setName("CD_LINK");
        item.setTableFieldName("CDLNK");
        item.setDataElement(element);
        model.add(item);
        
        data.addNumberFactory("CMODEL_ID");
        
        /*
         * Item de modelo de documento complexo
         */
        model = data.getModel("COMPLEX_MODEL_ITEM", "CPLXMITEM", null);
        
        // identificador
        element = new DataElement();
        element.setName("COMPLEX_MODEL_ITEM.ID");
        element.setLength(6);
        element.setType(DataType.NUMC);
        
        item = new DocumentModelItem();
        item.setName("ID");
        item.setTableFieldName("IDENT");
        item.setDataElement(element);
        model.add(item);
        model.add(new DocumentModelKey(item));
        
        // modelo referência
        element = config.cmodelname.getDataElement();
        
        item = new DocumentModelItem();
        item.setName("COMPLEX_MODEL");
        item.setTableFieldName("MDLNM");
        item.setDataElement(element);
        item.setReference(config.cmodelname);
        model.add(item);
        
        // modelo
        element = modelname.getDataElement();
        
        item = new DocumentModelItem();
        item.setName("MODEL");
        item.setTableFieldName("MODEL");
        item.setDataElement(element);
        item.setReference(modelname);
        model.add(item);
    }
}
