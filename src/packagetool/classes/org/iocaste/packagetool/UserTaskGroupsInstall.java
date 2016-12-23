package org.iocaste.packagetool;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.ModelInstall;
import org.iocaste.appbuilder.common.StandardInstallContext;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.DummyElement;
import org.iocaste.documents.common.DummyModelItem;

public class UserTaskGroupsInstall extends AbstractInstallObject {

    @Override
    protected void execute(StandardInstallContext context) throws Exception {
        DataElement groupid, username, packagename;
        DocumentModelItem userkey;
        ModelInstall model;
        
        groupid = elementchar("USER_TASKS_GROUPS.ID", 30, DataType.UPPERCASE);
        username = new DummyElement("LOGIN.USERNAME");
        packagename = new DummyElement("PACKAGE.NAME");
        userkey = new DummyModelItem("LOGIN", "USERNAME");
        
        model = modelInstance("USER_TASKS_GROUPS", "USRTASKGRP");
        model.key(
                "ID", "IDENT", groupid);
        model.reference(
                "USERNAME", "UNAME", username, userkey);
        model.reference(
                "GROUP", "GRPID", getItem("groupid"));
        model.item(
                "PACKAGE", "PKGNM", packagename);
    }

}
