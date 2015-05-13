package org.iocaste.login;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.protocol.Iocaste;
import org.iocaste.shell.common.Const;

public class Connect extends AbstractActionHandler {

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        String username, secret, locale;
        Iocaste iocaste = new Iocaste(context.function);
        
        username = getdfst("login", "USERNAME");
        secret = getdfst("login", "SECRET");
        locale = getdfst("login", "LOCALE");
        
        if (!iocaste.login(username, secret, locale)) {
            message(Const.ERROR, "invalid.login");
            return;
        }
        
        context.function.export("username", username);
        if (iocaste.isInitialSecret())
            redirect("changesecret");
        else
            context.function.exec("iocaste-tasksel", "main");
    }
}
