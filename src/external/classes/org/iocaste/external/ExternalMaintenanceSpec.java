package org.iocaste.external;

import java.util.Map;

import org.iocaste.appbuilder.common.cmodelviewer.Context;
import org.iocaste.appbuilder.common.cmodelviewer.MaintenanceSpec;
import org.iocaste.documents.common.DocumentModel;

public class ExternalMaintenanceSpec extends MaintenanceSpec {

    @Override
    protected void tabs(Context extcontext, Map<String, DocumentModel> models) {
        String tablename;
        
        for (String name : models.keySet()) {
            tabbedpaneitem("tabs", name);
            if (name.equals("items")) {
                nodelist(name, "import");
                dataform("import", "importobj");
                button("import", "importmodel");
            }
            
            tablename = name.concat("_table");
            tabletool(name, tablename);
            extcontext.models.put(tablename, name);
        }
    }
}
