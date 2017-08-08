package org.iocaste.runtime.common.portal.signup;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.user.User;
import org.iocaste.runtime.common.RuntimeEngine;
import org.iocaste.runtime.common.application.AbstractActionHandler;
import org.iocaste.runtime.common.portal.PortalContext;
import org.iocaste.shell.common.Const;

public class PortalSignUpSave extends AbstractActionHandler<PortalContext> {

    @Override
    protected void execute(PortalContext context) throws Exception {
        ExtendedObject object;
        String appname, username;
        User user;
        long userid;
        RuntimeEngine runtime;
        
        context.email = getst("user", "EMAIL");
        context.secret = getst("user", "SECRET");
        appname = context.getAppName();
        object = select("PORTAL_USERS", appname, context.email);
        if (object != null) {
            message(Const.ERROR, "user.exists");
            return;
        }
        
        runtime = context.runtime();
        userid = runtime.getNextNumber("PRTLUSRS");
        username = String.format("PTL%05d", userid);
        user = new User();
        user.setUsername(username);
        runtime.create(user);
        runtime.setUserPassword(user.getUsername(), context.secret, false);
        runtime.assignAuthorization(username, "BASE");
        runtime.assignAuthorization(username, "APPBUILDER");
        if (context.userprofile != null)
            runtime.assignAuthorization(username, context.userprofile);
        
        object = instance("PORTAL_USERS");
        Documents.move(object, getobject("user"));
        object.setNS(appname);
        object.set("USERNAME", username);
        save(object);

        message(Const.STATUS, "user.created");
        execute("connect");
    }

}
