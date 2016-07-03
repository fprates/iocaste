package org.iocaste.workbench.install;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.ModelInstall;
import org.iocaste.appbuilder.common.StandardInstallContext;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DummyElement;

public class ProjectHeadInstall extends AbstractInstallObject {

    @Override
    protected void execute(StandardInstallContext context) {
        ModelInstall model;
        DataElement projectname, text, profile;

        projectname = elementchar("WB_PROJECT_NAME", 32, false);
        profile = new DummyElement("USER_PROFILE.NAME");
        text = elementchar("WB_TEXT", 32, false);
        
        /*
         * project header
         */
        model = tag("header", modelInstance(
                "WB_PROJECT_HEAD", "WBPRJCTHD"));
        tag("projectkey", model.key(
                "PROJECT_NAME", "PRJNM", projectname));
        model.item(
                "PROFILE", "PROFL", profile);
        model.item(
                "TEXT", "PRJTX", text);
    }
    
}

