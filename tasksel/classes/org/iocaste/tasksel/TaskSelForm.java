package org.iocaste.tasksel;

import java.util.List;

import org.hibernate.Session;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.protocol.HibernateUtil;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.ControlData;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Menu;
import org.iocaste.shell.common.MenuItem;
import org.iocaste.shell.common.Text;
import org.iocaste.shell.common.ViewData;


public class TaskSelForm extends AbstractPage {
    
    /**
     * Visão para adição de programas
     * @param vdata Visão
     * @throws Exception
     */
    public final void add(ViewData vdata) throws Exception {
        DataForm dataform;
        Text text;
        Documents documents = new Documents(this);
        Container container = new Form(null, "add_entry");
        DocumentModel model = documents.getModel("task_entry");
        
        Task[] tasks = getTasks();
        
        for (Task task : tasks) {
            text = new Text(container, task.getName());
            text.setText(task.getName());
        }
        
        dataform = new DataForm(container, "entry");
        dataform.addAction("save");
        dataform.importModel(model);
        
        for (Element element : dataform.getElements()) {
            if (!element.isDataStorable())
                continue;
            
            ((InputComponent)element).setObligatory(true);
        }
        
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
     * Visão geral de tarefas
     * @param view Visão
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
     * Controlador geral de tarefas
     * @param controldata
     * @param view
     */
    public final void run(ControlData cdata, ViewData vdata) {
        MenuItem mitem = ((Menu)vdata.getElement("run")).getSelectedItem();
        
        cdata.redirect(mitem.getFunction(), mitem.getParameter("entry"));
    }
    
    /**
     * Controlador de adição de tarefas
     * @param cdata Controlador
     * @param vdata Visão
     */
    public final void save(ControlData cdata, ViewData vdata) {
        String name = ((DataItem)vdata.getElement("entry.name")).getValue();
        String app = ((DataItem)vdata.getElement("entry.app")).getValue();
        String entry = ((DataItem)vdata.getElement("entry.point")).getValue();
        
        insertAppEntry(name, app, entry);
        cdata.message(Const.STATUS, "insert.entry.successful");
    }
}
