package org.iocaste.tasksel;

import org.iocaste.protocol.Function;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.View;

public class Request {
    
    /**
     * 
     * @param view
     * @param function
     */
    public static final void grouprun(View view, Function function) {
        String command = ((InputComponent)view.getElement("groupcommand")).
                get();
        String[] parsed = Common.parseCommand(command, view, function);
        
        parsed = parsed[0].split("\\s");
        Common.run(view, parsed);
    }

    /**
     * 
     * @param view
     * @param function
     */
    public static final void run(View view, Function function) {
        String[] parsed;
        DataForm form = view.getElement("selector");
        DataItem cmdline = form.get("command");
        String command = cmdline.get();
        
        if (Shell.isInitial(command))
            return;
        
        cmdline.set(null);
        parsed = Common.parseCommand(command, view, function);
        if (parsed == null) {
            view.setFocus(cmdline);
            return;
        }
        
        Common.run(view, parsed);
    }

}
