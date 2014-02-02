package org.iocaste.workbench.install;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.documents.common.DummyElement;
import org.iocaste.packagetool.common.InstallData;

public class Models {
    public static final void install(InstallData data) {
        DataElement element, sourceid, sourcename;
        DocumentModel model;
        DocumentModelItem item, projectname, packageid;
        
        /*
         * Projeto
         */
        model = data.getModel("WB_PROJECT", "WB_PROJECT", null);
        
        element = new DataElement("WB_PROJECT_NAME");
        element.setType(DataType.CHAR);
        element.setLength(32);
        element.setUpcase(true);
        projectname = new DocumentModelItem("PROJECT_NAME");
        projectname.setTableFieldName("PRJNM");
        projectname.setDataElement(element);
        model.add(new DocumentModelKey(projectname));
        model.add(projectname);
        
        element = new DataElement("WB_PROJECT_ID");
        element.setType(DataType.NUMC);
        element.setLength(3);
        item = new DocumentModelItem("PROJECT_ID");
        item.setTableFieldName("PRJID");
        item.setDataElement(element);
        model.add(item);
        
        item = new DocumentModelItem("SOURCE_OBJ");
        item.setTableFieldName("SRCOB");
        item.setDataElement(new DummyElement("TXTED_TEXTNAME"));
        model.add(item);

        sourceid = new DataElement("WB_SOURCE_ID");
        sourceid.setType(DataType.NUMC);
        sourceid.setLength(9);
        item = new DocumentModelItem("SOURCE_ID");
        item.setTableFieldName("SRCID");
        item.setDataElement(sourceid);
        model.add(item);

        sourcename = new DataElement("WB_SOURCE_NAME");
        sourcename.setType(DataType.CHAR);
        sourcename.setLength(128);
        sourcename.setUpcase(false);
        item = new DocumentModelItem("ENTRY_CLASS");
        item.setTableFieldName("ENTRY");
        item.setDataElement(sourcename);
        model.add(item);
        
        element = new DataElement("WB_SERVICE_CLASS");
        element.setType(DataType.CHAR);
        element.setLength(128);
        element.setUpcase(false);
        item = new DocumentModelItem("SERVICE_CLASS");
        item.setTableFieldName("SRVCL");
        item.setDataElement(element);
        model.add(item);
        
        data.addNumberFactory("WBPROJECTID");
        
        /*
         * Pacote
         */
        model = data.getModel("WB_PACKAGE", "WB_PACKAGE", null);
        
        element = new DataElement("WB_PACKAGE_ID");
        element.setType(DataType.NUMC);
        element.setLength(6);
        packageid = new DocumentModelItem("PACKAGE_ID");
        packageid.setTableFieldName("PKGID");
        packageid.setDataElement(element);
        model.add(new DocumentModelKey(packageid));
        model.add(packageid);
        
        item = new DocumentModelItem("PROJECT_NAME");
        item.setTableFieldName("PRJNM");
        item.setDataElement(projectname.getDataElement());
        item.setReference(projectname);
        model.add(item);
        
        element = new DataElement("WB_PACKAGE_NAME");
        element.setType(DataType.CHAR);
        element.setLength(128);
        element.setUpcase(false);
        item = new DocumentModelItem("PACKAGE_NAME");
        item.setTableFieldName("PKGNM");
        item.setDataElement(element);
        model.add(item);
        
        /*
         * Fonte
         */
        model = data.getModel("WB_SOURCE", "WB_SOURCE", null);
        
        item = new DocumentModelItem("SOURCE_ID");
        item.setTableFieldName("SRCID");
        item.setDataElement(sourceid);
        model.add(new DocumentModelKey(item));
        model.add(item);
        
        item = new DocumentModelItem("PROJECT_NAME");
        item.setTableFieldName("PRJNM");
        item.setDataElement(projectname.getDataElement());
        item.setReference(projectname);
        model.add(item);

        item = new DocumentModelItem("PACKAGE_ID");
        item.setTableFieldName("PKGID");
        item.setDataElement(packageid.getDataElement());
        item.setReference(packageid);
        model.add(item);

        item = new DocumentModelItem("SOURCE_NAME");
        item.setTableFieldName("SRCNM");
        item.setDataElement(sourcename);
        model.add(item);
    }
}
