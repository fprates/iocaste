package org.iocaste.tasksel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.Frame;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.NodeList;
import org.iocaste.shell.common.PageControl;
import org.iocaste.shell.common.Parameter;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.View;

public class Main extends AbstractPage {
    private static final byte USER_GROUPS = 0;
    private static final byte ENTRY = 1;
    private static final byte TASK_TEXT = 2;
    private static final String[] QUERIES = {
        "from USER_TASKS_GROUPS where USERNAME = ?",
        "from TASK_ENTRY where GROUP = ?",
        "from TASK_ENTRY_TEXT where TASK = ? and LANGUAGE = ?"
    };
    
    public Main() {
        export("install", "install");
    }
    
    /**
     * 
     * @return
     * @throws Exception
     */
    private final List<TasksList> getLists(Locale locale) throws Exception {
        TasksList list;
        TaskEntry entry;
        ExtendedObject[] result, iresult, mobject;
        List<TasksList> lists;
        int taskid;
        String groupname, language, taskname, username = 
                new Iocaste(this).getUsername();
        Documents documents = new Documents(this);
        
        result = documents.select(QUERIES[USER_GROUPS], username);
        if (result == null)
            return null;
        
        language = locale.toString();
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
                taskname = iobject.getValue("NAME");
                taskid = iobject.getValue("ID");
                
                entry = new TaskEntry();
                entry.setName(taskname);
                
                mobject = documents.selectLimitedTo(QUERIES[TASK_TEXT], 1,
                        taskid, language);
                
                if (mobject != null)
                    entry.setText((String)mobject[0].getValue("TEXT"));
                
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
    public final void grouprun(View view) throws Exception {
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
    public final void help(View vdata) {
        vdata.clearParameters();
        vdata.export("topic", "tasksel-index");
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
    public final void main(View view) throws Exception {
        Link link;
        Frame frame;
        NodeList group;
        List<TasksList> lists;
        DataForm form;
        DataItem cmdline;
        Parameter groupcommand;
        String taskname, text;
        Form container = new Form(view, "main");
        PageControl pagecontrol = new PageControl(container);
        StandardContainer groups = new StandardContainer(container, "groups");
        
        pagecontrol.add("help");
        groups.setStyleClass("groups");
        setCustomStyleSheet(view);
        
        /*
         * tarefas pré-definidas
         */
        lists = getLists(view.getLocale());
        if (lists != null)
            for (TasksList tasks : lists) {
                frame = new Frame(groups, tasks.getName());
                frame.setStyleClass("tasksel_frame");
                group = new NodeList(frame, tasks.getName());
                for (TaskEntry entry : tasks.getEntries()) {
                    groupcommand = new Parameter(container, "groupcommand");
                    taskname = entry.getName();
                    text = entry.getText();
                    
                    link = new Link(group, taskname, "grouprun");
                    link.setText((text == null)? taskname : text);
                    link.add(groupcommand, taskname);
                }
            }
        
        /*
         * linha de comando
         */
        form = new DataForm(container, "selector");
        cmdline = new DataItem(form, Const.TEXT_FIELD, "command");
        
        cmdline.setLength(80);
        new Button(container, "run");
        
        view.setFocus("command");
        view.setTitle("task-selector");
    }
    
    /**
     * 
     * @param vdata
     * @throws Exception
     */
    public final void run(View vdata) throws Exception {
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
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    private final void setCustomStyleSheet(View view) throws Exception {
        Map<String, Map<String, String>> defaultsheet;
        Map<String, String> style;
        
        defaultsheet = new Shell(this).getStyleSheet(view);
        
        style = new HashMap<String, String>();
        style.putAll(defaultsheet.get(".frame"));
        style.put("display", "inline");
        style.put("vertical-align", "top");
        defaultsheet.put(".tasksel_frame", style);
        
        style = new HashMap<String, String>();
        style.put("background-color", "#000000");
        style.put("padding", "3px");
        style.put("margin-bottom", "3px");
        defaultsheet.put(".groups", style);
        
        view.setStyleSheet(defaultsheet);
    }
}
