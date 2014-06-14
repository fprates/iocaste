package org.iocaste.tasksel;

import org.iocaste.protocol.Function;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.user.Authorization;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.View;

public class Request {
    
    public static int call(View view, Function function, String command) {
        Iocaste iocaste;
        Authorization authorization;
        String[] parsed;
        
        authorization = new Authorization("LINK.CALL");
        authorization.setObject("LINK");
        authorization.setAction("CALL");
        authorization.add("LINK", command);
        iocaste = new Iocaste(function);
        if (!iocaste.isAuthorized(authorization))
            return 1;
        
        parsed = Common.parseCommand(command, function, view);
        parsed = parsed[0].split("\\s");
        Common.run(view, parsed);
        return 0;
    }
    /**
     * 
     * @param context
     */
    public static final void grouprun(AbstractContext context) {
        String command = ((InputComponent)context.view.
                getElement("groupcommand")).get();

        if (call(context.view, context.function, command) == 0)
            return;
        
        context.view.message(Const.ERROR, "not.authorized");
    }
}
