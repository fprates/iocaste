package org.iocaste.examples;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.packagetool.common.InstallData;
import org.iocaste.packagetool.common.TaskGroup;
import org.iocaste.protocol.user.Authorization;
import org.iocaste.protocol.user.UserProfile;

public class Install {

    public static final InstallData init() {
        Authorization authorization;
        UserProfile userprofile;
        DocumentModel model;
        DocumentModelItem item;
        DataElement element;
        TaskGroup taskgroup;
        InstallData data = new InstallData();

        data.link("REPORT", "iocaste-examples");
        data.link("PING", "iocaste-examples @ping");
        data.link("EXTERN", "iocaste-examples @extern");
        taskgroup = new TaskGroup("EXAMPLES");
        taskgroup.add("REPORT");
        taskgroup.add("PING");
        taskgroup.add("EXTERN");
        data.add(taskgroup);
        
        authorization = new Authorization("EXAMPLES.EXECUTE");
        authorization.setAction("EXECUTE");
        authorization.setObject("APPLICATION");
        authorization.add("APPNAME", "iocaste-examples");
        data.add(authorization);
        
        userprofile = new UserProfile("EXAMPLES");
        userprofile.add(authorization);
        data.add(userprofile);
        
//        model = data.getModel("EXMPLRPT_CUSTOMERS", null, null);
//        
//        // identificador
//        element = new DataElement("EXMPLRPT_CUSTOMERS.ID");
//        element.setType(DataType.CHAR);
//        element.setLength(10);
//        element.setUpcase(false);
//        
//        item = new DocumentModelItem("CUSTOMER_ID");
//        item.setDataElement(element);
//        model.add(item);
//
//        // identificador
//        element = new DataElement("EXMPLRPT_CUSTOMERS.ID");
//        element.setType(DataType.CHAR);
//        element.setLength(10);
//        element.setUpcase(false);
//        
//        item = new DocumentModelItem("COMPANY_NAME");
//        item.setDataElement(element);
//        model.add(item);
//
//        // identificador
//        element = new DataElement("EXMPLRPT_CUSTOMERS.ID");
//        element.setType(DataType.CHAR);
//        element.setLength(10);
//        element.setUpcase(false);
//        
//        item = new DocumentModelItem("CONTACT_NAME");
//        item.setDataElement(element);
//        model.add(item);
//
//        // identificador
//        element = new DataElement("EXMPLRPT_CUSTOMERS.ID");
//        element.setType(DataType.CHAR);
//        element.setLength(10);
//        element.setUpcase(false);
//        
//        item = new DocumentModelItem("CONTACT_TITLE");
//        item.setDataElement(element);
//        model.add(item);
//
//        // identificador
//        element = new DataElement("EXMPLRPT_CUSTOMERS.ID");
//        element.setType(DataType.CHAR);
//        element.setLength(10);
//        element.setUpcase(false);
//        
//        item = new DocumentModelItem("ADDRESS");
//        item.setDataElement(element);
//        model.add(item);
//
//        // identificador
//        element = new DataElement("EXMPLRPT_CUSTOMERS.ID");
//        element.setType(DataType.CHAR);
//        element.setLength(10);
//        element.setUpcase(false);
//        
//        item = new DocumentModelItem("CITY");
//        item.setDataElement(element);
//        model.add(item);
//
//        // identificador
//        element = new DataElement("EXMPLRPT_CUSTOMERS.ID");
//        element.setType(DataType.CHAR);
//        element.setLength(10);
//        element.setUpcase(false);
//        
//        item = new DocumentModelItem("POSTAL_CODE");
//        item.setDataElement(element);
//        model.add(item);
//
//        // identificador
//        element = new DataElement("EXMPLRPT_CUSTOMERS.ID");
//        element.setType(DataType.CHAR);
//        element.setLength(10);
//        element.setUpcase(false);
//        
//        item = new DocumentModelItem("COUNTRY");
//        item.setDataElement(element);
//        model.add(item);
//
//        // identificador
//        element = new DataElement("EXMPLRPT_CUSTOMERS.ID");
//        element.setType(DataType.CHAR);
//        element.setLength(10);
//        element.setUpcase(false);
//        
//        item = new DocumentModelItem("PHONE");
//        item.setDataElement(element);
//        model.add(item);
//
//        // identificador
//        element = new DataElement("EXMPLRPT_CUSTOMERS.ID");
//        element.setType(DataType.CHAR);
//        element.setLength(10);
//        element.setUpcase(false);
//        
//        item = new DocumentModelItem("FAX");
//        item.setDataElement(element);
//        model.add(item);
//        
//        
//        data.addValues(model, "ALFKI",
//                "Alfreds Futterkiste",
//                "Maria Anders",
//                "Sales Representative",
//                "Obere Str. 57",
//                "Berlin",
//                "12209",
//                "Germany",
//                "030-0074321",
//                "030-0076545");
        
        return data;
    }
}
