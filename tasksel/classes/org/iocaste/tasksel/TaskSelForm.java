package org.iocaste.tasksel;

import java.util.List;

import org.hibernate.Session;
import org.iocaste.protocol.HibernateUtil;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.ControlData;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataFormItem;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.Menu;
import org.iocaste.shell.common.MenuItem;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.ViewData;

public class TaskSelForm extends AbstractPage {
    
    public final void add(ViewData vdata) {
        DataForm dataform;
        Text text;
        Container container = new Form(null, "add_entry");
        
        Task[] tasks = getTasks();
        
        for (Task task : tasks) {
            text = new Text(container, task.getName());
            text.setText(task.getName());
        }
        
        dataform = new DataForm(container, "entry");
        dataform.addAction("save");
        
        new DataFormItem(dataform, Const.TEXT_FIELD, "name");
        new DataFormItem(dataform, Const.TEXT_FIELD, "app");
        new DataFormItem(dataform, Const.TEXT_FIELD, "entry");
        
        vdata.addContainer(container);
        vdata.setTitle("add.tasksel.entry");
    }
    
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
        MenuItem mitem;
        Container form = new Form(null, "main");
        Menu menu = new Menu(form, "run");
        Task[] tasks = getTasks();
        
        for (Task task : tasks) {
            mitem = new MenuItem(menu, task.getName(), task.getApp());
            mitem.putParameter("entry", task.getEntry());
        }
        
        view.setTitle("infosis-front");
        view.addContainer(form);
    }
    
    /**
     * 
     * @param controldata
     * @param view
     */
    public final void run(ControlData controldata, ViewData view) {
        MenuItem mitem = ((Menu)view.getElement("run")).getSelectedItem();
        
        controldata.redirect(mitem.getFunction(), mitem.getParameter("entry"));
    }
}
