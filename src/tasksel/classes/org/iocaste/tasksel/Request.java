package org.iocaste.tasksel;

import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.PageContext;

public class Request {
    
    /**
     * 
     * @param context
     */
    public static final void grouprun(PageContext context) {
        String command = ((InputComponent)context.view.
                getElement("groupcommand")).get();
        String[] parsed = Common.parseCommand(command, context);
        
        parsed = parsed[0].split("\\s");
        Common.run(context.view, parsed);
    }
}
