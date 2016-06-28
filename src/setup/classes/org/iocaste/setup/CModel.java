package org.iocaste.setup;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.documents.common.DummyElement;
import org.iocaste.documents.common.DummyModelItem;
import org.iocaste.packagetool.common.InstallData;

public class CModel {
    
    /**
     * 
     * @param data
     * @param config
     */
    public static final void install(InstallData data, Config config) {
        DocumentModel model;
        DataElement element, modelname;
        DocumentModelItem modelkey, item;
        
        modelname = new DummyElement("MODEL.NAME");
        
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
        modelkey = new DummyModelItem("MODEL", "NAME");
        item = new DocumentModelItem("MODEL");
        item.setTableFieldName("MODEL");
        item.setDataElement(modelname);
        item.setReference(modelkey);
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
        item.setDataElement(modelname);
        model.add(item);
        
        // tipo de modelo
        element = new DataElement("CMODEL_MODEL_TYPE");
        element.setType(DataType.NUMC);
        element.setLength(1);
        item = new DocumentModelItem("MODEL_TYPE");
        item.setTableFieldName("MDLTY");
        item.setDataElement(element);
        model.add(item);
        
        // formato de chave caracter
        element = new DataElement("CMODEL_KEY_FORMAT");
        element.setType(DataType.CHAR);
        element.setLength(10);
        element.setUpcase(false);
        item = new DocumentModelItem("KEY_FORMAT");
        item.setTableFieldName("KFRMT");
        item.setDataElement(element);
        model.add(item);
        
        // digitos de chave numérica
        element = new DataElement("CMODEL_KEY_DIGITS");
        element.setType(DataType.NUMC);
        element.setLength(1);
        item = new DocumentModelItem("KEY_DIGITS");
        item.setTableFieldName("KDGTS");
        item.setDataElement(element);
        model.add(item);
        
        // índice do item para mapa
        element = new DummyElement("MODELITEM.NAME");
        item = new DocumentModelItem("INDEX");
        item.setTableFieldName("ITIDX");
        item.setDataElement(element);
        model.add(item);
    }
}
