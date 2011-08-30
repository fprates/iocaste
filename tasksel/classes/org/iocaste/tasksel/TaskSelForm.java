package org.iocaste.tasksel;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.shell.common.AbstractForm;
import org.iocaste.shell.common.ControlData;
import org.iocaste.shell.common.Menu;
import org.iocaste.shell.common.MenuItem;
import org.iocaste.shell.common.ViewData;

public class TaskSelForm extends AbstractForm {
    private final Task taskInstance(String name, String app, String entry) {
        Task task = new Task();
        task.setName(name);
        task.setApp(app);
        task.setEntry(entry);
        
        return task;
    }
    
    private final Task[] getTasks() {
        List<Task> tasks = new ArrayList<Task>();
        
        tasks.add(taskInstance("infosis", "iocaste-infosis", "main"));
        tasks.add(taskInstance("office", "iocaste-office", "main"));
        tasks.add(taskInstance("tools", "iocaste-core-utils", "main"));
        
        return tasks.toArray(new Task[0]);
    }
    
    /**
     * 
     * @param view
     */
    public final void main(ViewData view) {
        Menu menu = new Menu(null, "run");
        Task[] tasks = getTasks();
        
        for (Task task : tasks)
            new MenuItem(menu, task.getName(), task.getApp());
        
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
        
        controldata.redirect(action, "main");
    }
}
