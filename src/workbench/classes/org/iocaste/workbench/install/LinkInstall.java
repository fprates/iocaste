package org.iocaste.workbench.install;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.ModelInstall;
import org.iocaste.appbuilder.common.StandardInstallContext;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DummyElement;

public class LinkInstall extends AbstractInstallObject {

    @Override
    protected void execute(StandardInstallContext context) {
        ModelInstall model;
        DataElement command, linkid, linkname, groupid;

        linkid = elementchar("WB_LINK_ID", 50, true);
        linkname = new DummyElement("TASKS.NAME");
        command = new DummyElement("TASKS.COMMAND");
        groupid = new DummyElement("TASKS_GROUPS.NAME");
        
        /*
         * Links
         */
        model = tag("links", modelInstance(
                "WB_LINKS", "WBLINKS"));
        model.key(
                "LINK_ID", "LNKID", linkid);
        model.reference(
                "PROJECT", "PRJNM", getItem("projectkey"));
        model.item(
                "NAME", "LNKNM", linkname);
        model.item(
                "COMMAND", "CMMND", command);
        model.item(
                "GROUP", "GRPID", groupid);
    }
    
}