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
import org.iocaste.shell.common.Frame;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.NodeList;
import org.iocaste.shell.common.Parameter;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.ViewData;

public class Main extends AbstractPage {
    private static final byte USER_GROUPS = 0;
    private static final byte ENTRY = 1;
    private static final String[] QUERIES = {
        "from USER_TASKS_GROUPS where USERNAME = ?",
        "from TASK_ENTRY where GROUP = ?"
    };
    
    public Main() {
        export("install", "install");
    }
    
    /**
     * 
     * @return
     * @throws Exception
     */
    private final List<TasksList> getLists() throws Exception {
        TasksList list;
        TaskEntry entry;
        ExtendedObject[] result, iresult;
        List<TasksList> lists;
        String groupname;
        Documents documents = new Documents(this);
        String username = new Iocaste(this).getUsername();
        
        result = documents.select(QUERIES[USER_GROUPS], username);
        if (result == null)
            return null;
        
        lists = new ArrayList<TasksList>();
        for (ExtendedObject object : result) {
            groupname = object.getValue("GROUP");
            
            list = new TasksList();
            list.setName(groupname);
            lists.add(list);
            
            iresult = documents.select(QUERIES[ENTRY], groupname);
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
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public final void grouprun(ViewData view) throws Exception {
        String command = ((InputComponent)view.getElement("groupcommand")).
                get();
        String[] parsed = Common.parseCommand(command, view, this);
        
        Common.run(view, parsed);
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
        Link link;
        Frame frame;
        NodeList group;
        List<TasksList> lists;
        DataForm form;
        DataItem cmdline;
        Parameter groupcommand;
        Container container = new Form(view, "main");
        
        /*
         * tarefas pré-definidas
         */
        lists = getLists();
        if (lists != null)
            for (TasksList tasks : lists) {
                frame = new Frame(container, tasks.getName());
                group = new NodeList(frame, tasks.getName());
                for (TaskEntry entry : tasks.getEntries()) {
                    groupcommand = new Parameter(container, "groupcommand");
                    
                    link = new Link(group, entry.getName(), "grouprun");
                    link.setText(entry.getName());
                    link.add(groupcommand, entry.getName());
                }
            }
        
        /*
         * linha de comando
         */
        form = new DataForm(container, "selector");
        cmdline = new DataItem(form, Const.TEXT_FIELD, "command");
        
        cmdline.setLength(80);
        new Button(container, "run");
        
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
        String[] parsed;
        DataForm form = vdata.getElement("selector");
        DataItem cmdline = form.get("command");
        String command = cmdline.get();
        
        if (Shell.isInitial(command))
            return;
        
        cmdline.set(null);
        parsed = Common.parseCommand(command, vdata, this);
        if (parsed == null)
            return;
        
        Common.run(vdata, parsed);
    }
}
