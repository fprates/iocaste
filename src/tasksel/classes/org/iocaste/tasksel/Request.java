package org.iocaste.tasksel;

import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.user.Authorization;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.AbstractContext;

public class Request {
    
    /**
     * 
     * @param context
     */
    public static final void grouprun(AbstractContext context) {
        Iocaste iocaste;
        Authorization authorization;
        String command = ((InputComponent)context.view.
                getElement("groupcommand")).get();

        authorization = new Authorization("LINK.CALL");
        authorization.setObject("LINK");
        authorization.setAction("CALL");
        authorization.add("LINK", command);
        iocaste = new Iocaste(context.function);
        if (!iocaste.isAuthorized(authorization)) {
            context.view.message(Const.ERROR, "not.authorized");
            return;
        }
        
        String[] parsed = Common.parseCommand(command, context);
        
        parsed = parsed[0].split("\\s");
        Common.run(context.view, parsed);
    }
}
