package org.iocaste.tasksel;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.ViewData;

public class Bookmark {
    private static final int CREATE = 0;
    private static final int EDIT = 1;
    private static final String[] TITLE = {
        "bookmark-task-create",
        "bookmark-task-update"
    };
    
    /**
     * 
     * @param view
     * @param function
     * @throws Exception
     */
    public static final void main(ViewData view, Function function)
            throws Exception {
        DataItem item;
        byte mode = view.getParameter("mode");
        Container container = new Form(null, "main");
        DataForm form = new DataForm(container, "task");
        
        form.importModel(new Documents(function).getModel("TASKS"));

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
    
    /**
     * 
     * @param view
     * @param function
     * @throws Exception
     */
    public static final void newentry(ViewData view, Function function)
            throws Exception {
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
        
        documents = new Documents(function);
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
     * @param function
     * @throws Exception
     */
    public static final void save(ViewData view, Function function)
            throws Exception {
        DataForm form = view.getElement("task");
        Documents documents = new Documents(function);
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
