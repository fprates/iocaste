package org.iocaste.tasksel;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Iocaste;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.ViewData;

public class Main extends AbstractPage {
    private static final int CREATE = 0;
    private static final int EDIT = 1;
    private static final String[] TITLE = {
        "bookmark-task-create",
        "bookmark-task-update"
    };
    
    public final void bookmark(ViewData view) throws Exception {
        DataItem item;
        byte mode = view.getParameter("mode");
        Container container = new Form(null, "main");
        DataForm form = new DataForm(container, "task");
        
        form.importModel(new Documents(this).getModel("TASKS"));

        item = form.get("NAME");
        switch (mode) {
        case CREATE:
            item.setValue((String)view.getParameter("name"));
            break;
        case EDIT:
            form.setObject((ExtendedObject)view.getParameter("task"));
            break;
        }
        
        item.setEnabled(false);
        
        new Button(container, "savetask");
        
        view.setNavbarActionEnabled("back", true);
        view.setFocus("COMMAND");
        view.setTitle(TITLE[mode]);
        view.addContainer(container);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.AbstractPage#help(
     *     org.iocaste.shell.common.ViewData)
     */
    @Override
    public final void help(ViewData vdata) {
        vdata.addParameter("topic", "tasksel-index");
        vdata.redirect("iocaste-help", "view");
    }
    
    /**
     * Visão geral de tarefas
     * @param view Visão
     * @throws Exception
     */
    public final void main(ViewData view) throws Exception {
        Container container = new Form(null, "main");
        DataForm form = new DataForm(container, "selector");
        DataItem cmdline = new DataItem(form, Const.TEXT_FIELD, "command");
        
        cmdline.setLength(128);
        new Button(container, "run");
        new Button(container, "save");
        
        view.setNavbarActionEnabled("help", true);
        view.setFocus("command");
        view.addContainer(container);
        view.setTitle("task-selector");
    }
    
    /**
     * Controlador geral de tarefas
     * @param view
     */
    public final void run(ViewData vdata) throws Exception {
        String[] parsed, values;
        Iocaste iocaste;
        ExtendedObject task;
        DataForm form = vdata.getElement("selector");
        DataItem cmdline = form.get("command");
        String command = cmdline.getValue();
        String page = "main";
        String app = null;
        
        if (command == null || command.length() == 0)
            return;
        else
            command.trim();
        
        cmdline.setValue("");
        parsed = command.split("\\s");
        vdata.clearParameters();

        task = new Documents(this).getObject("TASKS", parsed[0].toUpperCase());
        if (task == null) {
            vdata.message(Const.ERROR, "command.not.found");
            vdata.setFocus("command");
            return;
        }

        parsed[0] = (String)task.getValue("COMMAND");
        
        for (int i = 0; i < parsed.length; i++) {
            switch (i) {
            case 0:
                app = parsed[i];
                break;
            default:
                if (parsed[i].startsWith("@")) {
                    page = parsed[i].substring(1);
                    break;
                }
                
                values = parsed[i].split("=");
                if (values.length < 2)
                    break;
                
                vdata.export(values[0], values[1]);
                break;
            }
        }
        
        if (app == null)
            return;
        
        iocaste = new Iocaste(this);
        if (!iocaste.isAppEnabled(app)) {
            vdata.message(Const.ERROR, "app.not.enabled");
        } else {
            vdata.setReloadableView(true);
            vdata.redirect(app, page);
        }
    }
    
    /**
     * 
     * @param view
     */
    public final void save(ViewData view) throws Exception {
        byte mode;
        ExtendedObject task;
        Documents documents;
        DataForm form = view.getElement("selector");
        String command = form.get("command").getValue();
        
        command = (command == null)? "" : command.toUpperCase();
        
        if (command.equals("")) {
            view.message(Const.ERROR, "task.name.is.required");
            view.setFocus("command");
            return;
        }
        
        documents = new Documents(this);
        task = documents.getObject("TASKS", command);
        
        if (task != null) {
            mode = EDIT;
            view.export("task", task);
        } else {
            mode = CREATE;
            view.export("name", command);
        }
        
        view.export("mode", mode);
        view.setReloadableView(true);
        view.redirect(null, "bookmark");
    }
    
    /**
     * 
     * @param view
     */
    public final void savetask(ViewData view) throws Exception {
        DataForm form = view.getElement("task");
        Documents documents = new Documents(this);
        byte mode = view.getParameter("mode");
        ExtendedObject task = form.getObject();
        
        switch (mode) {
        case CREATE:
            documents.save(task);
            break;
        case EDIT:
            documents.modify(task);
            break;
        }
            
        documents.commit();
        
        view.message(Const.STATUS, "task.saved.successfully");
        view.redirect(null, "main");
    }
}
