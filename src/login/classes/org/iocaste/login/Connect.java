package org.iocaste.login;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Iocaste;
import org.iocaste.shell.common.Const;

public class Connect extends AbstractActionHandler {

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        Context extcontext;
        String secret, locale, task;
        Iocaste iocaste = new Iocaste(context.function);
        
        extcontext = getExtendedContext();
        extcontext.uname = getdfst("login", "USERNAME");
        secret = getdfst("login", "SECRET");
        locale = getdfst("login", "LOCALE");
        
        if (!iocaste.login(extcontext.uname, secret, locale)) {
            message(Const.ERROR, "invalid.login");
            return;
        }
        
        context.function.export("username", extcontext.uname);
        if (iocaste.isInitialSecret()) {
            redirect("changesecret");
            return;
        }
        
        task = getUserTask(extcontext.uname);
        if (task == null) {
            context.function.exec("iocaste-tasksel", "main");
            return;
        }

        taskredirect(task);
    }
    
    protected final String getUserTask(String username) {
        ExtendedObject object;
        
        object = getObject("LOGIN_EXTENSION", username);
        return (object == null)? null : object.getst("TASK");
    }
}
