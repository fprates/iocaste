package org.iocaste.login;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.protocol.Iocaste;
import org.iocaste.shell.common.Const;

public class ChangeSecret extends Connect {

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        String task;
        Context extcontext;
        String secret = getdfst("chgscrt", "SECRET");
        String confirm = getdfst("chgscrt", "CONFIRM");
        
        if (secret.equals(confirm)) {
            new Iocaste(context.function).setUserPassword(secret);
            
            /*
             * não queremos que essa seja a página inicial
             */
            context.function.dontPushPage();
            extcontext = getExtendedContext();
            task = getUserTask(extcontext.uname);
            if (task == null) {
                context.function.exec("iocaste-tasksel", "main");
                return;
            }

            taskredirect(task);
            return;
        }
        
        message(Const.ERROR, "password.mismatch");
    }
}
