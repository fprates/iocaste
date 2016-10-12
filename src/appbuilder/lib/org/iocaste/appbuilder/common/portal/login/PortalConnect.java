package org.iocaste.appbuilder.common.portal.login;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.portal.PortalContext;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.ExtendedObject;
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
        ComplexDocument usertree;
        PortalContext extcontext = getExtendedContext();
        String form = forms.get(context.view.getPageName());
        String email = getdfst(form, "EMAIL");
        ExtendedObject object = getObject("PORTAL_USERS_REF", email);
        
        if (object == null) {
            message(Const.ERROR, "invalid.user");
            return;
        }

        usertree = getDocument("PORTAL_USER_TREE", object.getl("ID"));
        object = usertree.getHeader();
        if (!object.getst("SECRET").equals(getdfst(form, "SECRET"))) {
            message(Const.ERROR, "invalid.user");
            return;
        }

        init("main", extcontext);
        redirect("main");
    }
    
}
