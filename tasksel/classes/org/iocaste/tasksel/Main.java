package org.iocaste.tasksel;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.NodeList;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.ViewData;

public class Main extends AbstractPage {
    
    public Main() {
        export("install", "install");
    }
    
    private final List<TasksList> getLists() throws Exception {
        TasksList list;
        TaskEntry entry;
        ExtendedObject[] result, iresult;
        List<TasksList> lists;
        String groupname;
        Documents documents = new Documents(this);
        String username = new Iocaste(this).getUsername();
        String query = "from USER_TASKS_GROUPS where USERNAME = ?";
        
        result = documents.select(query, username);
        
        if (result == null)
            return null;
        
        query = "from TASK_ENTRY where GROUP = ?";
        lists = new ArrayList<TasksList>();
        for (ExtendedObject object : result) {
            groupname = object.getValue("GROUP");
            
            list = new TasksList();
            list.setName(groupname);
            lists.add(list);
            
            iresult = documents.select(query, groupname);
            if (iresult == null)
                continue;
            
            for (ExtendedObject iobject : iresult) {
                entry = new TaskEntry();
                entry.setName((String)iobject.getValue("NAME"));
                
                list.add(entry);
            }
        }
        
        return lists;
    }
    
    public final void grouprun(ViewData view) {
        
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
     * @throws Exception
     */
    public final InstallData install(Message message) throws Exception {
        return Install.init(this);
    }
    
    /**
     * Visão geral de tarefas
     * @param view Visão
     * @throws Exception
     */
    public final void main(ViewData view) throws Exception {
        NodeList group;
        List<TasksList> lists;
        Container container = new Form(view, "main");
        DataForm form = new DataForm(container, "selector");
        DataItem cmdline = new DataItem(form, Const.TEXT_FIELD, "command");
        
        cmdline.setLength(80);
        new Button(container, "run");
        
        lists = getLists();
        
        if (lists != null)
            for (TasksList tasks : lists) {
                group = new NodeList(container, tasks.getName());
                for (TaskEntry entry : tasks.getEntries())
                    new Link(group, entry.getName(), "grouprun").
                            setText(entry.getName());
            }
        
        view.setNavbarActionEnabled("help", true);
        view.setFocus("command");
        view.setTitle("task-selector");
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
}
