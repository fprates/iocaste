package org.iocaste.tasksel;

import java.util.List;

import org.hibernate.Session;
import org.iocaste.protocol.HibernateUtil;
import org.iocaste.shell.common.AbstractForm;
import org.iocaste.shell.common.ControlData;
import org.iocaste.shell.common.Menu;
import org.iocaste.shell.common.MenuItem;
import org.iocaste.shell.common.ViewData;

public class TaskSelForm extends AbstractForm {
    
    @SuppressWarnings("unchecked")
    private final Task[] getTasks() {
        List<Task> tasks;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        
        tasks = session.createQuery("from Task").list();
        
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
