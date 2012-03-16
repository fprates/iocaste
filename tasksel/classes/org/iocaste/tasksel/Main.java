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
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public final void bookmark(ViewData view) throws Exception {
        Bookmark.main(view, this);
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
        new Button(container, "newentry");
        
        view.setNavbarActionEnabled("help", true);
        view.setFocus("command");
        view.addContainer(container);
        view.setTitle("task-selector");
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public final void newentry(ViewData view) throws Exception {
        Bookmark.newentry(view, this);
    }
    
    /**
     * 
     * @param vdata
     * @throws Exception
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
     * @throws Exception
     */
    public final void save(ViewData view) throws Exception {
        Bookmark.save(view, this);
    }
}
