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
import org.iocaste.shell.common.TextField;
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
        
        new DataFormItem(dataform, Const.TEXT_FIELD, "entry.name");
        new DataFormItem(dataform, Const.TEXT_FIELD, "entry.app");
        new DataFormItem(dataform, Const.TEXT_FIELD, "entry.point");
        
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
    
    private final void insertAppEntry(String app, String name, String entry) {
        Task task = new Task();
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        
        task.setApp(app);
        task.setName(name);
        task.setEntry(entry);
        
        session.beginTransaction();
        session.save(task);
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
    public final void run(ControlData cdata, ViewData vdata) {
        MenuItem mitem = ((Menu)vdata.getElement("run")).getSelectedItem();
        
        cdata.redirect(mitem.getFunction(), mitem.getParameter("entry"));
    }
    
    public final void save(ControlData cdata, ViewData vdata) {
        String name = ((TextField)vdata.getElement("entry.name")).getValue();
        String app = ((TextField)vdata.getElement("entry.app")).getValue();
        String entry = ((TextField)vdata.getElement("entry.point")).getValue();
        
        if (name == null || app == null || entry == null)
            cdata.message(Const.ERROR, "field.is.obligatory");
        
        insertAppEntry(name, app, entry);
        cdata.message(Const.STATUS, "insert.entry.successful");
    }
}
