package org.iocaste.tasksel;

import org.iocaste.shell.common.AbstractForm;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.ViewData;

public class TaskSelForm extends AbstractForm {

    public TaskSelForm() {
        ViewData main = new ViewData();
        Table table = new Table(null, 1);
        TableItem item1 = new TableItem(table);
        TableItem item2 = new TableItem(table);
        TableItem item3 = new TableItem(table);
        
        item1.add(new Link(null, "info"));
        item2.add(new Link(null, "office"));
        item3.add(new Link(null, "coreutils"));
        
        main.setTitle("infosis-front");
        main.setContainer(table);
        
        addView("main", main);
    }
    
    public final void info() {
        redirect("iocaste-infosis", "main");
    }
    
    public final void office() {
        redirect("iocaste-office", "main");
    }
    
    public final void coreutils() {
        redirect("iocaste-core-utils", "main");
    }
}
