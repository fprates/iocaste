package org.iocaste.appbuilder.common.portal.login;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.portal.PortalContext;
//import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Iocaste;
import org.iocaste.shell.common.Const;

public class PortalConnect extends AbstractActionHandler {
    private Map<String, String> forms;
    
    public PortalConnect() {
        forms = new HashMap<>();
        forms.put("signup", "signup");
        forms.put("authentic", "login");
    }
    
    @Override
    protected void execute(PageBuilderContext context) throws Exception {
//        ComplexDocument usertree;
        String form, appname;
        ExtendedObject object;
        boolean connected;
        PortalContext extcontext = getExtendedContext();
        
        if (extcontext.email == null) {
            form = forms.get(context.view.getPageName());
            extcontext.email = getdfst(form, "EMAIL");
            extcontext.secret = getdfst(form, "SECRET");
        }
        
        appname = context.view.getAppName();
        object = getObject("PORTAL_USERS", appname, extcontext.email);
        if (object == null) {
            message(Const.ERROR, "invalid.user");
            return;
        }

//        usertree = getDocument("PORTAL_USER_TREE", object.getl("ID"));
//        object = usertree.getHeader();
//        if (!object.getst("SECRET").equals(extcontext.secret)) {
//            message(Const.ERROR, "invalid.user");
//            return;
//        }

        connected = new Iocaste(context.function).
                login(object.getst("USERNAME"), extcontext.secret, "pt_BR");
        if (!connected) {
            message(Const.ERROR, "invalid.username.password");
            return;
        }
        
        extcontext.secret = null;
        init("main", extcontext);
        redirect("main");
    }
    
}
