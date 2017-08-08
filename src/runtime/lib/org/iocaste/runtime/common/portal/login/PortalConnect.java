package org.iocaste.runtime.common.portal.login;

import java.util.HashMap;
import java.util.Map;

//import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.runtime.common.application.AbstractActionHandler;
import org.iocaste.runtime.common.portal.PortalContext;
import org.iocaste.shell.common.Const;

public class PortalConnect extends AbstractActionHandler<PortalContext> {
    private Map<String, String> forms;
    
    public PortalConnect() {
        forms = new HashMap<>();
        forms.put("signup", "signup");
        forms.put("authentic", "login");
    }
    
    @Override
    protected void execute(PortalContext context) throws Exception {
//        ComplexDocument usertree;
        String form, appname, pagename;
        ExtendedObject object;
        boolean connected;
        
        pagename = context.getPageName();
        if (pagename.equals("authentic")) {
            form = forms.get(pagename);
            context.email = getst(form, "EMAIL");
            context.secret = getst(form, "SECRET");
        }
        
        appname = context.getAppName();
        object = getextobj("PORTAL_USERS", appname, context.email);
        if (object == null)
            message(Const.ERROR, "invalid.user");

//        usertree = getDocument("PORTAL_USER_TREE", object.getl("ID"));
//        object = usertree.getHeader();
//        if (!object.getst("SECRET").equals(extcontext.secret)) {
//            message(Const.ERROR, "invalid.user");
//            return;
//        }

        context.username = object.getst("USERNAME");
        connected = context.runtime().
                login(context.username, context.secret, "pt_BR");
        if (!connected)
            message(Const.ERROR, "invalid.username.password");
        
        context.secret = null;
        if (context.getPage().getActionHandler("load") != null)
            execute("load");
        else
            back();
    }
    
}
