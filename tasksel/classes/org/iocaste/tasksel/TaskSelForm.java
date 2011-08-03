package org.iocaste.tasksel;

import org.iocaste.shell.common.AbstractForm;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.Parameter;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.ViewData;

public class TaskSelForm extends AbstractForm {
    private Parameter module;
    
    public TaskSelForm() {
        ViewData main = new ViewData();
        StandardContainer container = new StandardContainer(null);
        Table table = new Table(container, 1);
        TableItem item1 = new TableItem(table);
        TableItem item2 = new TableItem(table);
        TableItem item3 = new TableItem(table);
        Link info = new Link(null, "run");
        Link office = new Link(null, "run");
        Link coreutils = new Link(null, "run");
        
        module = new Parameter(container, "module");
        
        info.add(module, "info");
        office.add(module, "office");
        coreutils.add(module, "coreutils");
        
        item1.add(info);
        item2.add(office);
        item3.add(coreutils);
        
        main.setTitle("infosis-front");
        main.setContainer(container);
        
        addView("main", main);
    }
    
    public final void run() {
        String action = module.getValue();
        
        if (action.equals("info"))
            redirect("iocaste-infosis", "main");
        
        if (action.equals("office"))
            redirect("iocaste-office", "main");
        
        if (action.equals("coreutils"))
            redirect("iocaste-core-utils", "main");
    }
}
