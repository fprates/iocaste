package org.iocaste.setup;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.packagetool.common.InstallData;

public class CDocument {
    
    /**
     * 
     * @param data
     * @param config
     */
    public static final void install(InstallData data, Config config) {
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
        
        // data de criação
        element = new DataElement();
        element.setName("COMPLEX_DOCUMENT.DATA_CRIACAO");
        element.setType(DataType.DATE);
        element.setLength(10);
        
        item = new DocumentModelItem();
        item.setName("DATA_CRIACAO");
        item.setTableFieldName("DTDOC");
        item.setDataElement(element);
        model.add(item);
        
        // hora de criação
        element = new DataElement();
        element.setName("COMPLEX_DOCUMENT.HORA_CRIACAO");
        element.setType(DataType.TIME);
        element.setLength(8);
        
        item = new DocumentModelItem();
        item.setName("HORA_CRIACAO");
        item.setTableFieldName("HRDOC");
        item.setDataElement(element);
        model.add(item);
        
        // usuário de criação
        element = new DataElement();
        element.setName("COMPLEX_DOCUMENT.USUARIO_CRIACAO");
        element.setType(DataType.CHAR);
        element.setLength(12);
        element.setUpcase(true);
        
        item = new DocumentModelItem();
        item.setName("USUARIO_CRIACAO");
        item.setTableFieldName("USDOC");
        item.setDataElement(element);
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
