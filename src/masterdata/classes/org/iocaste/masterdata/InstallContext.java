package org.iocaste.masterdata;

import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.packagetool.common.InstallData;

public class InstallContext {
    public InstallData data;
    public DocumentModelItem currency, countryid;
    
    public InstallContext() {
        data = new InstallData();
    }
}
