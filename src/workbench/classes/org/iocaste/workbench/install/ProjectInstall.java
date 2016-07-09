package org.iocaste.workbench.install;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.ComplexModelInstall;
import org.iocaste.appbuilder.common.StandardInstallContext;

public class ProjectInstall extends AbstractInstallObject {

    @Override
    protected void execute(StandardInstallContext context) {
        ComplexModelInstall cmodel;
        
        /*
         * project document
         */
        cmodel = cmodelInstance("WB_PROJECT");
        cmodel.header("header");
        cmodel.document("screen", "screens").index = "NAME";
        cmodel.item("link", "links").index = "NAME";
        cmodel.document("model", "models");
        cmodel.document("class", "classes");
    }
}
