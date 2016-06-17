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
        DataElement element, configname, configvalue;
        DocumentModel model;
        
        configname = new DataElement("SHELL_CONFIG_NAME");
        configname.setType(DataType.CHAR);
        configname.setLength(20);
        configname.setUpcase(true);
        
        configvalue = new DataElement("SHELL_CONFIG_VALUE");
        configvalue.setType(DataType.CHAR);
        configvalue.setLength(64);
        configvalue.setUpcase(false);
        
        /*
         * tickets
         */
        model = data.getModel("SHELL_TICKETS", "SHELL004", null);
        
        element = new DataElement("SHELL_TICKET_ID");
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
        
        /*
         * shell config
         */
        model = data.getModel("SHELL_PROPERTIES", "SHELL006", null);
        item = new DocumentModelItem("NAME");
        item.setTableFieldName("CFGNM");
        item.setDataElement(configname);
        model.add(new DocumentModelKey(item));
        model.add(item);
        
        item = new DocumentModelItem("VALUE");
        item.setTableFieldName("CFGVL");
        item.setDataElement(configvalue);
        model.add(item);
        
        data.addValues(model, "LOGIN_MANAGER", "iocaste-login");
        data.addValues(model, "EXCEPTION_HANDLER", "iocaste-exhandler");
    }
}
