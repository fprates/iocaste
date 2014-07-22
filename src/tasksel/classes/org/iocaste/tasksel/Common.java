package org.iocaste.tasksel;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Function;
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
    public static final String[] parseCommand(
            String command, Function function, View view) {
        String[] parsed;
        ExtendedObject task;
        
        parsed = command.trim().split("\\s");
        view.clearExports();

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
    public static final void run(View view, String[] parsed) {
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
                
                view.setParameter(values[0], values[1]);
                break;
            }
        }
        
        if (app == null)
            return;
        
        view.setReloadableView(true);
        view.redirect(app, page, View.INITIALIZE);
    }

}
