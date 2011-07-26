package org.iocaste.tasksel;

import org.iocaste.shell.common.AbstractForm;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.ViewData;

public class TaskSelForm extends AbstractForm {

    public TaskSelForm() {
        ViewData main = new ViewData();
        StandardContainer container = new StandardContainer(null);
        Link link = new Link(container, "info");
        
        link.setText("iocaste-infosis");
        
        main.setTitle("infosis-front");
        main.setContainer(container);
        
        addView("main", main);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.AbstractForm#entry(java.lang.String)
     */
    @Override
    public final void entry(String action) {
        if (action.equals("info"))
            redirect("iocaste-infosis", "main");
        
    }
}
