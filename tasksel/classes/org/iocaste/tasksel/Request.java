package org.iocaste.tasksel;

import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.PageContext;
import org.iocaste.shell.common.Shell;

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
    
    /**
     * 
     * @param context
     */
    public static final void run(PageContext context) {
        String[] parsed;
        DataForm form = context.view.getElement("selector");
        DataItem cmdline = form.get("command");
        String command = cmdline.get();
        
        if (Shell.isInitial(command))
            return;
        
        cmdline.set(null);
        parsed = Common.parseCommand(command, context);
        if (parsed == null) {
            context.view.setFocus(cmdline);
            return;
        }
        
        Common.run(context.view, parsed);
    }

}
