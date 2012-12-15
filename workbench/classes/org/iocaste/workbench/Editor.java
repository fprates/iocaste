package org.iocaste.workbench;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DummyElement;
import org.iocaste.packagetool.common.InstallData;

public class Editor {

    public static final void install(InstallData data) {
        DocumentModel model;
        DocumentModelItem item;
        DataElement element;
        
        model = data.getModel("ICSTPRJ_EDITOR_HEADER", null, null);
        
        element = new DummyElement("PACKAGE.NAME");
        item = new DocumentModelItem("NAME");
        item.setDataElement(element);
        item.setSearchHelp("SH_PROJECT_HEADER");
        model.add(item);
        
        element = new DataElement("ICSTPRJ_EDITOR_HEADER.PACKAGE");
        element.setType(DataType.CHAR);
        element.setLength(128);
        element.setUpcase(false);
        item = new DocumentModelItem("PACKAGE");
        item.setDataElement(element);
        model.add(item);
        
        element = new DataElement("ICSTPRJ_EDITOR_HEADER.CLASS");
        element.setType(DataType.CHAR);
        element.setLength(64);
        element.setUpcase(false);
        item = new DocumentModelItem("CLASS");
        item.setDataElement(element);
        model.add(item);
    }
}
