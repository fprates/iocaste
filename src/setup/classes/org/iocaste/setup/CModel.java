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
        DocumentModelItem modelname, item, namespace;
        
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
        
        /*
         * cabeçalho namespace
         */
        model = data.getModel("NS_HEAD", "NSHEAD", null);
        
        // identificador
        element = new DataElement("NS_NAME");
        element.setType(DataType.CHAR);
        element.setLength(24);
        element.setUpcase(DataType.KEEPCASE);
        namespace = new DocumentModelItem("NAME");
        namespace.setTableFieldName("NSKEY");
        namespace.setDataElement(element);
        model.add(namespace);
        model.add(new DocumentModelKey(namespace));
        
        // nome do modelo complexo
        item = new DocumentModelItem("MODEL");
        item.setTableFieldName("MODEL");
        item.setDataElement(modelname.getDataElement());
        item.setReference(modelname);
        model.add(item);
        
        /*
         * modelos
         */
        model = data.getModel("NS_MODELS", "NSMODELS", null);
        
        // identificador
        element = new DataElement("NS_ITEM");
        element.setType(DataType.CHAR);
        element.setLength(27);
        element.setUpcase(DataType.KEEPCASE);
        item = new DocumentModelItem("ITEM");
        item.setTableFieldName("NSITM");
        item.setDataElement(element);
        model.add(item);
        model.add(new DocumentModelKey(item));
        
        // referência
        item = new DocumentModelItem("NAMESPACE");
        item.setTableFieldName("NSKEY");
        item.setDataElement(namespace.getDataElement());
        item.setReference(namespace);
        model.add(item);
        
        // complex model
        item = new DocumentModelItem("COMPLEX_MODEL");
        item.setTableFieldName("CMKEY");
        item.setDataElement(config.cmodelname.getDataElement());
        item.setReference(config.cmodelname);
        model.add(item);
    }
}
