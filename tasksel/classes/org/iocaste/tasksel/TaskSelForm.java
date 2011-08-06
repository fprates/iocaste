package org.iocaste.tasksel;

import org.iocaste.shell.common.AbstractForm;
import org.iocaste.shell.common.ControlData;
import org.iocaste.shell.common.Menu;
import org.iocaste.shell.common.MenuItem;
import org.iocaste.shell.common.ViewData;

public class TaskSelForm extends AbstractForm {
    
    /**
     * 
     * @param view
     */
    public final void main(ViewData view) {
        Menu menu = new Menu(null, "run");
        
        new MenuItem(menu, "info", "info");
        new MenuItem(menu, "office", "office");
        new MenuItem(menu, "tools", "coreutils");
        
        view.setTitle("infosis-front");
        view.setContainer(menu);
    }
    
    /**
     * 
     * @param controldata
     * @param view
     */
    public final void run(ControlData controldata, ViewData view) {
        String action = ((Menu)view.getElement("run")).getParameter().getValue();
        
        if (action.equals("info"))
            controldata.redirect("iocaste-infosis", "main");
        
        if (action.equals("office"))
            controldata.redirect("iocaste-office", "main");
        
        if (action.equals("coreutils"))
            controldata.redirect("iocaste-core-utils", "main");
    }
}
