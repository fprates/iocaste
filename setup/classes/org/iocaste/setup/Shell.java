package org.iocaste.setup;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.documents.common.DummyElement;
import org.iocaste.packagetool.common.InstallData;

public class Shell {

    public static final void install(InstallData data) {
        DocumentModelItem item;
        DataElement element;
        DocumentModel model = data.getModel("SHELL_TICKETS", "SHELL004", null);
        
        element = new DataElement("TICKET_ID");
        element.setType(DataType.CHAR);
        element.setLength(64);
        item = new DocumentModelItem("ID");
        item.setTableFieldName("TKTID");
        item.setDataElement(element);
        model.add(item);
        model.add(new DocumentModelKey(item));
        
        element = new DataElement("SHELL_APPNAME");
        element.setType(DataType.CHAR);
        element.setLength(64);
        item = new DocumentModelItem("APP_NAME");
        item.setTableFieldName("APPNM");
        item.setDataElement(element);
        model.add(item);
        
        element = new DataElement("SHELL_PAGENAME");
        element.setType(DataType.CHAR);
        element.setLength(64);
        item = new DocumentModelItem("PAGE_NAME");
        item.setTableFieldName("PAGEN");
        item.setDataElement(element);
        model.add(item);
        
        element = new DummyElement("LOGIN.USERNAME");
        item = new DocumentModelItem("USERNAME");
        item.setTableFieldName("USRNM");
        item.setDataElement(element);
        model.add(item);
        
        element = new DummyElement("LOGIN.SECRET");
        item = new DocumentModelItem("SECRET");
        item.setTableFieldName("SECRT");
        item.setDataElement(element);
        model.add(item);
        
        element = new DataElement("SHELL_LOCALE");
        element.setType(DataType.CHAR);
        element.setLength(5);
        item = new DocumentModelItem("LOCALE");
        item.setTableFieldName("LOCAL");
        item.setDataElement(element);
        model.add(item);
        
    }
}
