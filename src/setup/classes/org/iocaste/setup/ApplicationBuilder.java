package org.iocaste.setup;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.packagetool.common.InstallData;

public class ApplicationBuilder {

    public static final void install(InstallData data) {
        DocumentModel model;
        DocumentModelItem item;
        DataElement element;
        
        model = data.getModel("APP_BUILDER_VIEWS", "APPBLDRVWS", null);
        
        /*
         * id do item da visão
         */
        element = new DataElement("AB_VIEW_ID");
        element.setType(DataType.CHAR);
        element.setLength(40);
        element.setUpcase(true);
        item = new DocumentModelItem("VIEW_ID");
        item.setTableFieldName("VIEWID");
        item.setDataElement(element);
        model.add(new DocumentModelKey(item));
        model.add(item);
        
        /*
         * aplicação
         */
        element = new DataElement("AB_APP_NAME");
        element.setType(DataType.CHAR);
        element.setLength(60);
        element.setUpcase(false);
        item = new DocumentModelItem("APP_NAME");
        item.setTableFieldName("APPNM");
        item.setDataElement(element);
        model.add(item);
        
        /*
         * visão
         */
        element = new DataElement("AB_VIEW_NAME");
        element.setType(DataType.CHAR);
        element.setLength(35);
        element.setUpcase(false);
        item = new DocumentModelItem("VIEW_NAME");
        item.setTableFieldName("VIEWNM");
        item.setDataElement(element);
        model.add(item);
        
        /*
         * conteiner
         */
        element = new DataElement("AB_ELEMENT");
        element.setType(DataType.CHAR);
        element.setLength(40);
        element.setUpcase(false);
        item = new DocumentModelItem("CONTAINER");
        item.setTableFieldName("CNTNM");
        item.setDataElement(element);
        model.add(item);
        
        /*
         * nome
         */
        item = new DocumentModelItem("NAME");
        item.setTableFieldName("NAME");
        item.setDataElement(element);
        model.add(item);
        
        /*
         * tipo
         */
        
        element = new DataElement("AB_TYPE");
        element.setType(DataType.NUMC);
        element.setLength(2);
        item = new DocumentModelItem("TYPE");
        item.setTableFieldName("TYPE");
        item.setDataElement(element);
        model.add(item);
    }
}
