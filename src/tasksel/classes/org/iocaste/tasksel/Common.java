package org.iocaste.tasksel;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.user.Authorization;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.View;

public class Common {
    
    /**
     * 
     * @param command
     * @param function
     * @param view
     * @return
     */
    private static final String[] parseCommand(
            String command, AbstractPage function, View view) {
        String[] parsed;
        ExtendedObject task;
        
        parsed = command.trim().split("\\s");
        function.clearExports();

        if (parsed[0].length() >= 19) {
            view.message(Const.ERROR, "command.not.found");
            return null;
        }
        
        task = new Documents(function).
                getObject("TASKS", parsed[0].toUpperCase());
        
        if (task == null) {
            view.message(Const.ERROR, "command.not.found");
            return null;
        }

        parsed[0] = task.get("COMMAND");
        
        return parsed;
    }
    
    /**
     * 
     * @param view
     * @param parsed
     */
    private static final void run(
            AbstractPage function, View view, String[] parsed) {
        String[] values;
        String page = "main", app = null;
        
        for (int i = 0; i < parsed.length; i++) {
            switch (i) {
            case 0:
                app = parsed[i];
                break;
                
            default:
                if (parsed[i].startsWith("@")) {
                    page = parsed[i].substring(1);
                    break;
                }
                
                values = parsed[i].split("=");
                if (values.length < 2)
                    break;
                
                function.export(values[0], values[1]);
                break;
            }
        }
        
        if (app == null)
            return;
        
        function.exec(app, page);
    }
    
    public static int call(AbstractPage page, View view, String command) {
        Iocaste iocaste;
        Authorization authorization;
        String[] parsed;
        
        authorization = new Authorization("LINK.CALL");
        authorization.setObject("LINK");
        authorization.setAction("CALL");
        authorization.add("LINK", command);
        iocaste = new Iocaste(page);
        if (!iocaste.isAuthorized(authorization))
            return 1;
        
        parsed = parseCommand(command, page, view);
        parsed = parsed[0].split("\\s");
        run(page, view, parsed);
        return 0;
    }

}
