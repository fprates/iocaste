package org.iocaste.coreutils;

import org.iocaste.shell.common.AbstractForm;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.Menu;
import org.iocaste.shell.common.MenuItem;
import org.iocaste.shell.common.ViewData;

public class MainForm extends AbstractForm {

    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.AbstractForm#buildViews()
     */
    @Override
    protected final void buildViews() {
        Form form;
        ViewData view = getViewInstance("main");
        
        Menu mainMenu = new Menu(null, "run");
        new MenuItem(mainMenu, "user.add", "add");
        new MenuItem(mainMenu, "user.change", "change");
        view.setContainer(mainMenu);
        
        view = getViewInstance("form");
        form = new Form(null, "user");
        view.setContainer(form);
    }
    
    public final void submit() {
        
    }
}
