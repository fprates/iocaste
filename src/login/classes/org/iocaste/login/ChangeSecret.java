package org.iocaste.login;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.protocol.Iocaste;
import org.iocaste.shell.common.Const;

public class ChangeSecret extends AbstractActionHandler {

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        String secret = getdfst("chgscrt", "SECRET");
        String confirm = getdfst("chgscrt", "CONFIRM");
        
        if (secret.equals(confirm)) {
            new Iocaste(context.function).setUserPassword(secret);
            
            /*
             * não queremos que essa seja a página inicial
             */
            context.function.dontPushPage();
            context.function.exec("iocaste-tasksel", "main");
            return;
        }
        
        message(Const.ERROR, "password.mismatch");
    }
}
