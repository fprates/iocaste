package org.iocaste.tasksel;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
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
    
    /**
     * 
     * @return
     * @throws Exception
     */
    private final Task[] getTasks() throws Exception {
        Task[] tasks;
        Documents documents = new Documents(this);
        ExtendedObject[] objects =  documents.select("from task_entry", null);
        
        if (objects == null)
            return null;

        tasks = new Task[objects.length];
        
        for (int i = 0; i < objects.length; i++)
        	tasks[i] = objects[i].newInstance();
        
        return tasks;
    }
    
    private final void insertAppEntry(String app, String name, String entry) {
//        Task task = new Task();
//        Documents documents = new Documents(this);
//        
//        task.setApp(app);
//        task.setName(name);
//        task.setEntry(entry);
//        
//        documents.save(task);
    }
    
    /**
     * Visão geral de tarefas
     * @param view Visão
     * @throws Exception 
     */
    public final void main(ViewData view) throws Exception {
        MenuItem mitem;
        Container form = new Form(null, "main");
        Menu menu;
        Task[] tasks = getTasks();
        
        view.setTitle("infosis-front");
        view.addContainer(form);
        
        if (tasks == null) {
            new Text(form, "no.entries");
            return;
        }
        
        menu = new Menu(form, "run");
        
        for (Task task : tasks) {
            mitem = new MenuItem(menu, task.getName(), task.getApp());
            mitem.putParameter("entry", task.getEntry());
        }
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
