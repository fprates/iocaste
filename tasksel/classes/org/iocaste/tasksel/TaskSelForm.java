package org.iocaste.tasksel;

import org.iocaste.shell.common.AbstractForm;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.ViewData;

public class TaskSelForm extends AbstractForm {

    public TaskSelForm() {
        ViewData main = new ViewData();
        StandardContainer container = new StandardContainer(null);
        new Link(container, "info");
        new Link(container, "office");
        
        main.setTitle("infosis-front");
        main.setContainer(container);
        
        addView("main", main);
    }
    
    public final void info() {
        redirect("iocaste-infosis", "main");
    }
    
    public final void office() {
        redirect("iocaste-office", "main");
    }
}
