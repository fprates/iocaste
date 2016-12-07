package org.iocaste.external;

import java.util.Map;

import org.iocaste.appbuilder.common.cmodelviewer.CModelViewerContext;
import org.iocaste.appbuilder.common.cmodelviewer.MaintenanceSpec;
import org.iocaste.documents.common.ComplexModelItem;

public class ExternalMaintenanceSpec extends MaintenanceSpec {

    @Override
    protected void tabs(
            CModelViewerContext extcontext, Map<String, ComplexModelItem> models) {
        String tablename;
        
        for (String name : models.keySet()) {
            if (models.get(name).model == null)
                continue;
            tabbedpaneitem("tabs", name);
            if (name.equals("items")) {
                nodelist(name, "import");
                nodelistitem("import", "import_importobj");
                dataform("import_importobj", "importobj");
                nodelistitem("import", "import_importmodel");
                button("import_importmodel", "importmodel");
            }
            
            tablename = name.concat("_table");
            tabletool(name, tablename);
            extcontext.models.put(tablename, name);
        }
    }
}
