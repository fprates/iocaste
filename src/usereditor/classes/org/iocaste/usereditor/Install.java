package org.iocaste.usereditor;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.StandardInstallContext;
import org.iocaste.documents.common.SearchHelpData;

public class Install extends AbstractInstallObject {

    @Override
    protected void execute(StandardInstallContext context) throws Exception {
        SearchHelpData sh;
        
        sh = searchHelpInstance("SH_USER", "LOGIN");
        sh.setExport("USERNAME");
        sh.add("USERNAME");
        
        sh = searchHelpInstance("SH_USER_PROFILE", "USER_PROFILE");
        sh.setExport("NAME");
        sh.add("NAME");
        
        sh = searchHelpInstance("SH_TASKS_GROUPS", "TASKS_GROUPS");
        sh.setExport("NAME");
        sh.add("NAME");
    }
}
