package org.iocaste.tasksel;

import org.iocaste.shell.common.AbstractForm;
import org.iocaste.shell.common.Menu;
import org.iocaste.shell.common.MenuItem;
import org.iocaste.shell.common.ViewData;

public class TaskSelForm extends AbstractForm {
    private Menu menu;
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.AbstractForm#buildViews()
     */
    @Override
    protected final void buildViews() {
        ViewData main = getViewInstance("main");
        
        menu = new Menu(null, "run");
        new MenuItem(menu, "info", "info");
        new MenuItem(menu, "office", "office");
        new MenuItem(menu, "tools", "coreutils");
        
        main.setTitle("infosis-front");
        main.setContainer(menu);
    }
    
    public final void run() {
        String action = menu.getParameter().getValue();
        
        if (action.equals("info"))
            redirect("iocaste-infosis", "main");
        
        if (action.equals("office"))
            redirect("iocaste-office", "main");
        
        if (action.equals("coreutils"))
            redirect("iocaste-core-utils", "main");
    }
}
