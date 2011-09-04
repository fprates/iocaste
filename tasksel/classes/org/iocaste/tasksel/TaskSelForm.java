package org.iocaste.tasksel;

import java.util.List;

import org.hibernate.Session;
import org.iocaste.protocol.HibernateUtil;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.ControlData;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.Menu;
import org.iocaste.shell.common.MenuItem;
import org.iocaste.shell.common.ViewData;

public class TaskSelForm extends AbstractPage {
    
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
        Container form = new Form(null, "main");
        Menu menu = new Menu(form, "run");
        Task[] tasks = getTasks();
        
        for (Task task : tasks)
            new MenuItem(menu, task.getName(), task.getApp());
        
        view.setTitle("infosis-front");
        view.addContainer(form);
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
