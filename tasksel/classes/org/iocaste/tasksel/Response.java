package org.iocaste.tasksel;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Iocaste;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.NodeList;
import org.iocaste.shell.common.PageContext;
import org.iocaste.shell.common.PageControl;
import org.iocaste.shell.common.Parameter;
import org.iocaste.shell.common.View;

public class Response {
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
    
    /**
     * 
     * @param context
     * @return
     */
    private static final Map<String, Set<TaskEntry>> getLists(
            PageContext context) {
        Set<TaskEntry> entries;
        TaskEntry entry;
        ExtendedObject[] result, mobject;
        Map<String, Set<TaskEntry>> lists;
        int taskid;
        String groupname, language, taskname, username;
        Documents documents = new Documents(context.function);
        
        username = new Iocaste(context.function).getUsername();
        result = documents.select(QUERIES[USER_ENTRIES], username);
        if (result == null)
            return null;
        
        language = context.view.getLocale().toString(); 
        lists = new LinkedHashMap<>();
        for (ExtendedObject object : result) {
            groupname = object.getValue("GROUP");
            if (lists.containsKey(groupname)) {
                entries = lists.get(groupname);
            } else {
                entries = new LinkedHashSet<>();
                lists.put(groupname, entries);
            }
            
            taskname = object.getValue("NAME");
            taskid = object.geti("ID");
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
    
    public static final void grouprun(PageContext context) {
        InputComponent input = context.view.getElement("groupcommand");
        String command = input.get();
        String[] parsed = Common.parseCommand(command, context);
        
        Common.run(context.view, parsed);
    }

    public static final Map<String, Set<TaskEntry>> init(PageContext context) {
        return getLists(context);
    }
    
    /**
     * 
     * @param lists
     * @param context
     */
    public static final void main(Map<String, Set<TaskEntry>> lists,
            PageContext context) {
        Link link;
        NodeList groups, tasklist;
        Set<TaskEntry> entries;
        DataForm form;
        DataItem cmdline;
        Parameter groupcommand;
        String taskname, text;
        Form container = new Form(context.view, "main");
        PageControl pagecontrol = new PageControl(container);
        
        pagecontrol.add("help", PageControl.EXTERNAL);
        setCustomStyleSheet(context.view);
        
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
        
        context.view.setFocus(cmdline);
        context.view.setTitle("task-selector");
    }
    
    /**
     * 
     * @param view
     * @param function
     */
    private static final void setCustomStyleSheet(View view) {
        Map<String, Map<String, String>> defaultsheet;
        Map<String, String> style;
        
        style = new HashMap<>();
        style.put("padding", "0px");
        style.put("margin", "0px");
        
        defaultsheet = view.getStyleSheet();
        defaultsheet.put(".groups", style);
        
        style = new HashMap<>();
        style.put("float", "left");
        style.put("position", "relative");
        style.put("display", "inline");
        defaultsheet.put(".group_item", style);
        
        view.setStyleSheet(defaultsheet);
    }

}
