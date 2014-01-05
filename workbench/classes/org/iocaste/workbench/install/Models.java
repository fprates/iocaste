package org.iocaste.workbench.install;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.packagetool.common.InstallData;

public class Models {
    public static final void install(InstallData data) {
        DataElement element;
        DocumentModel model;
        DocumentModelItem item;
        
        model = data.getModel("WB_PROJECT", "WB_PROJECT", null);
        
        element = new DataElement("WB_PROJECT_NAME");
        element.setType(DataType.CHAR);
        element.setLength(32);
        element.setUpcase(true);
        item = new DocumentModelItem("PROJECT_NAME");
        item.setTableFieldName("PRJNM");
        item.setDataElement(element);
        model.add(new DocumentModelKey(item));
        model.add(item);
        
        element = new DataElement("WB_PROJECT_ID");
        element.setType(DataType.NUMC);
        element.setLength(6);
        item = new DocumentModelItem("PROJECT_ID");
        item.setTableFieldName("PRJID");
        item.setDataElement(element);
        model.add(item);
        
        data.addNumberFactory("WBPROJECTID");
    }
}
