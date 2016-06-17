package org.iocaste.setup;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DocumentModelKey;
import org.iocaste.documents.common.DummyElement;
import org.iocaste.documents.common.DummyModelItem;
import org.iocaste.packagetool.common.InstallData;
import org.iocaste.packagetool.common.SearchHelpData;

public class User {

    public static final void install(InstallData data) {
        DocumentModel model;
        DocumentModelItem item;
        SearchHelpData shd;
        
        model = data.getModel("LOGIN_EXTENSION", "LOGINEXT", null);
        item = new DocumentModelItem("USERNAME");
        item.setTableFieldName("USRNM");
        item.setDataElement(new DummyElement("LOGIN.USERNAME"));
        model.add(new DocumentModelKey(item));
        model.add(item);
        
        item = new DocumentModelItem("TASK");
        item.setTableFieldName("TSKNM");
        item.setDataElement(new DummyElement("TASKS.NAME"));
        item.setReference(new DummyModelItem("TASKS", "NAME"));
        item.setSearchHelp("SH_TASKS");
        model.add(item);
        
        shd = new SearchHelpData("SH_TASKS");
        shd.setModel("TASKS");
        shd.setExport("NAME");
        shd.add("NAME");
        shd.add("COMMAND");
        data.add(shd);
    }
}
