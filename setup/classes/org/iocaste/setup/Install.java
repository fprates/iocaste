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
        DocumentModelItem modelname, item, modelid;
        
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
        
        modelid = new DocumentModelItem();
        modelid.setName("NAME");
        modelid.setTableFieldName("IDENT");
        modelid.setDataElement(element);
        model.add(modelid);
        model.add(new DocumentModelKey(modelid));

        // índice
        element = new DataElement();
        element.setName("COMPLEX_MODEL.ID");
        element.setType(DataType.NUMC);
        element.setLength(7);
        
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
        
        data.addNumberFactory("CMODEL_ID");
        
        /*
         * Item de modelo de documento complexo
         */
        model = data.getModel("COMPLEX_MODEL_ITEM", "CPLXMITEM", null);
        
        // identificador
        element = new DataElement();
        element.setName("COMPLEX_MODEL_ITEM.ID");
        element.setLength(10);
        element.setType(DataType.NUMC);
        
        item = new DocumentModelItem();
        item.setName("ID");
        item.setTableFieldName("IDENT");
        item.setDataElement(element);
        model.add(item);
        model.add(new DocumentModelKey(item));
        
        // modelo referência
        element = modelid.getDataElement();
        
        item = new DocumentModelItem();
        item.setName("COMPLEX_MODEL");
        item.setTableFieldName("MDLNM");
        item.setDataElement(element);
        item.setReference(modelid);
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
        element = new DataElement();
        element.setName("COMPLEX_DOCUMENT.ID");
        element.setLength(10);
        element.setType(DataType.NUMC);
        
        docid = new DocumentModelItem();
        docid.setName("ID");
        docid.setTableFieldName("IDENT");
        docid.setDataElement(element);
        
        model.add(docid);
        model.add(new DocumentModelKey(docid));
        
        /*
         * Item de documento complexo
         */
        model = data.getModel("COMPLEX_DOCUMENT_ITEM", "CPLXDITEM", null);
        
        // identificador
        element = new DataElement();
        element.setName("COMPLEX_DOCUMENT_ITEM.ID");
        element.setLength(15);
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
}
