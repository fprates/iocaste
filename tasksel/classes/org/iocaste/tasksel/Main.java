package org.iocaste.tasksel;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

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
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.NodeList;
import org.iocaste.shell.common.PageControl;
import org.iocaste.shell.common.Parameter;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.View;

public class Main extends AbstractPage {
    private static final byte TASK_TEXT = 0;
    private static final byte USER_ENTRIES = 1;
    private static final String[] QUERIES = {
        "from TASK_ENTRY_TEXT where TASK = ? and LANGUAGE = ?",
        "select TASK_ENTRY.GROUP,TASK_ENTRY.NAME,TASK_ENTRY.ID " +
            "from USER_TASKS_GROUPS " +
            "inner join TASK_ENTRY on " +
            "USER_TASKS_GROUPS.GROUP = TASK_ENTRY.GROUP " +
            "where USERNAME = ?"
    };
    
    public Main() {
        export("install", "install");
    }
    
    /**
     * 
     * @param locale
     * @return
     * @throws Exception
     */
    private final Map<String, Set<TaskEntry>> getLists(Locale locale)
            throws Exception {
        Set<TaskEntry> entries;
        TaskEntry entry;
        ExtendedObject[] result, mobject;
        Map<String, Set<TaskEntry>> lists;
        int taskid;
        String groupname, language, taskname, username;
        Documents documents = new Documents(this);
        
        username = new Iocaste(this).getUsername();
        result = documents.select(QUERIES[USER_ENTRIES], username);
        if (result == null)
            return null;
        
        language = locale.toString(); 
        lists = new LinkedHashMap<String, Set<TaskEntry>>();
        for (ExtendedObject object : result) {
            groupname = object.getValue("GROUP");
            if (lists.containsKey(groupname)) {
                entries = lists.get(groupname);
            } else {
                entries = new LinkedHashSet<TaskEntry>();
                lists.put(groupname, entries);
            }
            
            taskname = object.getValue("NAME");
            taskid = object.getValue("ID");
            entry = new TaskEntry();
            entry.setName(taskname);
            entries.add(entry);
            mobject = documents.selectLimitedTo(QUERIES[TASK_TEXT], 1,
                    taskid, language);
            
            if (mobject != null)
                entry.setText((String)mobject[0].getValue("TEXT"));
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
        vdata.setParameter("topic", "tasksel-index");
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
        NodeList groups, tasklist;
        Set<TaskEntry> entries;
        Map<String, Set<TaskEntry>> lists;
        DataForm form;
        DataItem cmdline;
        Parameter groupcommand;
        String taskname, text;
        Form container = new Form(view, "main");
        PageControl pagecontrol = new PageControl(container);
        
        pagecontrol.add("help", PageControl.EXTERNAL);
        setCustomStyleSheet(view);
        
        /*
         * linha de comando
         */
        form = new DataForm(container, "selector");
        cmdline = new DataItem(form, Const.TEXT_FIELD, "command");
        cmdline.setLength(80);
        new Button(container, "run");
        
        /*
         * tarefas pré-definidas
         */
        lists = getLists(view.getLocale());
        if (lists != null) {
            groups = new NodeList(container, "groups");
            groups.setStyleClass("groups");
            groups.setStyleClass(NodeList.ITEM, "group_item");
            
            for (String groupname : lists.keySet()) {
                entries = lists.get(groupname);
                tasklist = new NodeList(groups, groupname);
                tasklist.setListType(NodeList.DEFINITION);
                
                for (TaskEntry entry : entries) {
                    groupcommand = new Parameter(container, "groupcommand");
                    taskname = entry.getName();
                    text = entry.getText();
                    
                    link = new Link(tasklist, taskname, "grouprun");
                    link.setText((text == null)? taskname : text);
                    link.add(groupcommand, taskname);
                }
            }
        }
        
        view.setFocus(cmdline);
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
        if (parsed == null) {
            vdata.setFocus(cmdline);
            return;
        }
        
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
        
        style = new HashMap<String, String>();
        style.put("padding", "0px");
        style.put("margin", "0px");
        
        defaultsheet = new Shell(this).getStyleSheet(view);
        defaultsheet.put(".groups", style);
        
        style = new HashMap<String, String>();
        style.put("float", "left");
        style.put("position", "relative");
        style.put("display", "inline");
        defaultsheet.put(".group_item", style);
        
        view.setStyleSheet(defaultsheet);
    }
}
