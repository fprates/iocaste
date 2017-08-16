package org.iocaste.runtime.common.portal.login;

import java.util.HashMap;
import java.util.Map;

//import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.runtime.common.application.AbstractActionHandler;
import org.iocaste.runtime.common.application.Context;
import org.iocaste.runtime.common.portal.PortalContext;
import org.iocaste.shell.common.Const;

public class PortalConnect extends AbstractActionHandler<Context> {
    private Map<String, String> forms;
    
    public PortalConnect() {
        forms = new HashMap<>();
        forms.put("signup", "signup");
        forms.put("authentic", "login");
    }
    
    @Override
    protected void execute(Context context) throws Exception {
//        ComplexDocument usertree;
        String form, appname, pagename;
        ExtendedObject object;
        boolean connected;
        PortalContext portalctx = context.portalctx();
        
        pagename = context.getPageName();
        if (pagename.equals("authentic")) {
            form = forms.get(pagename);
            portalctx.email = getst(form, "EMAIL");
            portalctx.secret = getst(form, "SECRET");
        }
        
        appname = context.getAppName();
        object = getextobj("PORTAL_USERS", appname, portalctx.email);
        if (object == null)
            message(Const.ERROR, "invalid.user");

//        usertree = getDocument("PORTAL_USER_TREE", object.getl("ID"));
//        object = usertree.getHeader();
//        if (!object.getst("SECRET").equals(extcontext.secret)) {
//            message(Const.ERROR, "invalid.user");
//            return;
//        }

        portalctx.username = object.getst("USERNAME");
        connected = context.runtime().
                login(portalctx.username, portalctx.secret, "pt_BR");
        if (!connected)
            message(Const.ERROR, "invalid.username.password");
        
        portalctx.secret = null;
        if (context.getPage().getActionHandler("load") != null)
            execute("load");
        else
            back();
    }
    
}
