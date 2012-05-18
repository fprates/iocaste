package org.iocaste.tasksel;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.ViewData;

public class Main extends AbstractPage {
    
    public Main() {
        export("install", "install");
    }
    
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
     * 
     * @param message
     * @return
     */
    public final InstallData install(Message message) {
        return Install.self();
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public final void installcancel(ViewData view) throws Exception {
        back(view);
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public final void installok(ViewData view) throws Exception {
        Install.installok(view, this);
        back(view);
    }
    
    /**
     * Visão geral de tarefas
     * @param view Visão
     * @throws Exception
     */
    public final void main(ViewData view) throws Exception {
        Container container = new Form(view, "main");
        DataForm form = new DataForm(container, "selector");
        DataItem cmdline = new DataItem(form, Const.TEXT_FIELD, "command");
        
        cmdline.setLength(80);
        new Button(container, "run");
        new Button(container, "newentry");
        
        view.setNavbarActionEnabled("help", true);
        view.setFocus("command");
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
        ExtendedObject task = null;
        DataForm form = vdata.getElement("selector");
        DataItem cmdline = form.get("command");
        String command = cmdline.get(), page = "main", app = null;
        
        if (Shell.isInitial(command))
            return;
        else
            command.trim();
        
        cmdline.set(null);
        parsed = command.split("\\s");
        vdata.clearParameters();

        if (parsed[0].length() >= 19) {
            vdata.message(Const.ERROR, "command.not.found");
            vdata.setFocus("command");
            return;
        }
        
        task = new Documents(this).getObject("TASKS", parsed[0].toUpperCase());
        
        if (task == null) {
//            if (PackageTool.hasPackage(parsed[0])) {
//                vdata.setReloadableView(true);
//                vdata.export("package", parsed[0]);
//                
//                if (new PackageTool(this).isInstalled(parsed[0]))
//                    vdata.redirect(null, "uninstall");
//                else
//                    vdata.redirect(null, "install");
//                
//                return;
//            }
//            
            vdata.message(Const.ERROR, "command.not.found");
            vdata.setFocus("command");
            return;
        }

        parsed[0] = task.getValue("COMMAND");
        
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
        
        vdata.setReloadableView(true);
        vdata.redirect(app, page);
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public final void save(ViewData view) throws Exception {
        Bookmark.save(view, this);
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public final void uninstallcancel(ViewData view) throws Exception {
        back(view);
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public final void uninstallok(ViewData view) throws Exception {
        Install.removeok(view, this);
    }
}
