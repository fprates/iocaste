package org.iocaste.setup;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.documents.common.Documents;
import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Function;

public class Install {

    public static final InstallData init(Function function) {
        InstallData data = new InstallData();
        Config config = new Config();
        
        config.function = function;
        installCModel(data, config);
        installCDocument(data, config);
        
        return data;
    }
    
    public static final void installCModel(InstallData data, Config config) {
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
    
    public static final void installCDocument(InstallData data, Config config) {
        DocumentModel model;
        DataElement element;
        DocumentModelItem item, docid;
        
        /*
         * Documento complexo
         */
        model = data.getModel("COMPLEX_DOCUMENT", "CPLXDOC", null);
        
        // identificador
        docid = new DocumentModelItem();
        docid.setName("ID");
        docid.setTableFieldName("IDENT");
        docid.setDataElement(config.cdocumentid.getDataElement());
        model.add(docid);
        model.add(new DocumentModelKey(docid));
        
        // modelo de referência
        item = new DocumentModelItem();
        item.setName("COMPLEX_MODEL");
        item.setTableFieldName("MODEL");
        item.setDataElement(config.cmodelname.getDataElement());
        item.setReference(config.cmodelname);
        model.add(item);
        
        /*
         * Item de documento complexo
         */
        model = data.getModel("COMPLEX_DOCUMENT_ITEM", "CPLXDITEM", null);
        
        // identificador
        element = new DataElement();
        element.setName("COMPLEX_DOCUMENT_ITEM.ID");
        element.setLength(16);
        element.setType(DataType.NUMC);

        item = new DocumentModelItem();
        item.setName("ID");
        item.setTableFieldName("IDENT");
        item.setDataElement(element);
        model.add(item);
        model.add(new DocumentModelKey(item));
        
        // documento referência
        element = docid.getDataElement();
        
        item = new DocumentModelItem();
        item.setName("COMPLEX_DOCUMENT");
        item.setTableFieldName("DOCID");
        item.setDataElement(element);
        item.setReference(docid);
        model.add(item);
    }
}

class Config {
    public Function function;
    public DocumentModelItem cdocumentid, cmodelname;
}
