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
        
        // nome
        element = new DataElement("CMODEL_NAME");
        element.setType(DataType.CHAR);
        element.setUpcase(true);
        element.setLength(24);
        config.cmodelname = new DocumentModelItem("NAME");
        config.cmodelname.setTableFieldName("CMNAM");
        config.cmodelname.setDataElement(element);
        model.add(config.cmodelname);
        model.add(new DocumentModelKey(config.cmodelname));
        
        // modelo
        modelname = new Documents(config.function).getModel("MODEL").
                getModelItem("NAME");
        element = modelname.getDataElement();
        item = new DocumentModelItem("MODEL");
        item.setTableFieldName("MODEL");
        item.setDataElement(element);
        item.setReference(modelname);
        model.add(item);
        
        /*
         * Item de modelo de documento complexo
         */
        model = data.getModel("COMPLEX_MODEL_ITEM", "CPLXMITEM", null);
        
        // identificador
        element = new DataElement("CMODEL_ITEM_IDENT");
        element.setLength(49);
        element.setType(DataType.CHAR);
        element.setUpcase(false);
        item = new DocumentModelItem("IDENT");
        item.setTableFieldName("CMIID");
        item.setDataElement(element);
        model.add(new DocumentModelKey(item));
        model.add(item);
        
        // nome
        element = new DataElement("CMODEL_ITEM_NAME");
        element.setLength(24);
        element.setType(DataType.CHAR);
        element.setUpcase(false);
        item = new DocumentModelItem("NAME");
        item.setTableFieldName("CMINM");
        item.setDataElement(element);
        model.add(item);
        
        // modelo referência
        item = new DocumentModelItem("CMODEL");
        item.setTableFieldName("MDLNM");
        item.setDataElement(config.cmodelname.getDataElement());
        item.setReference(config.cmodelname);
        model.add(item);
        
        // modelo
        item = new DocumentModelItem("MODEL");
        item.setTableFieldName("MODEL");
        item.setDataElement(modelname.getDataElement());
        item.setReference(modelname);
        model.add(item);
    }
}
