package org.iocaste.appbuilder.common.portal.signup;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.portal.PortalContext;
import org.iocaste.authority.common.Authority;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.user.User;
import org.iocaste.shell.common.Const;

public class PortalSignUpSave extends AbstractActionHandler {

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        PortalContext extcontext;
        ExtendedObject object;
        String appname, username;
        User user;
        long userid;
        Iocaste iocaste;
        Authority authority;
        
        extcontext = getExtendedContext();
        extcontext.email = getdfst("user", "EMAIL");
        extcontext.secret = getdfst("user", "SECRET");
        appname = context.view.getAppName();
        object = getObject("PORTAL_USERS", appname, extcontext.email);
        if (object != null) {
            message(Const.ERROR, "user.exists");
            return;
        }
        
        new Documents(context.function).
                createNumberFactory("PRTLUSRS", appname);
        userid = getNextNumber("PRTLUSRS", appname);
        username = String.format("PTL%05d", userid);
        user = new User();
        user.setUsername(username);
        iocaste = new Iocaste(context.function);
        iocaste.create(user);
        iocaste.setUserPassword(
                user.getUsername(), extcontext.secret, false);

        authority = new Authority(context.function);
        authority.assign(username, "BASE");
        authority.assign(username, "APPBUILDER");
        authority.assign(username, "MOTELGOGO");
        
        object = instance("PORTAL_USERS");
        Documents.move(object, getdf("user"));
        object.setNS(appname);
        object.set("USERNAME", username);
        save(object);

        message(Const.STATUS, "user.created");
        execute("connect");
    }

}
