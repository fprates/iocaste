package org.iocaste.tasksel;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.protocol.Iocaste;
import org.iocaste.shell.common.ExpandBar;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.PageContext;
import org.iocaste.shell.common.PageControl;
import org.iocaste.shell.common.Parameter;
import org.iocaste.shell.common.StyleSheet;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.TableItem;

public class Response {
    
    /**
     * 
     * @param context
     * @return
     */
    private static final Map<String, Set<TaskEntry>> getLists(
            PageContext context) {
        Query query;
        Set<TaskEntry> entries;
        TaskEntry entry;
        ExtendedObject[] result, mobject;
        Map<String, Set<TaskEntry>> lists;
        int taskid;
        String groupname, language, taskname, username;
        Documents documents = new Documents(context.function);
        
        username = new Iocaste(context.function).getUsername();
        query = new Query();
        query.addColumns(
                "TASK_ENTRY.GROUP", "TASK_ENTRY.NAME", "TASK_ENTRY.ID");
        query.setModel("USER_TASKS_GROUPS");
        query.join("TASK_ENTRY", "USER_TASKS_GROUPS.GROUP", "GROUP");
        query.andEqual("USERNAME", username);
        result = documents.select(query);
        if (result == null)
            return null;
        
        language = context.view.getLocale().toString(); 
        lists = new LinkedHashMap<>();
        for (ExtendedObject object : result) {
            groupname = object.get("GROUP");
            if (lists.containsKey(groupname)) {
                entries = lists.get(groupname);
            } else {
                entries = new LinkedHashSet<>();
                lists.put(groupname, entries);
            }
            
            taskname = object.get("NAME");
            taskid = object.geti("ID");
            entry = new TaskEntry();
            entry.setName(taskname);
            entries.add(entry);
            
            query = new Query();
            query.setModel("TASK_ENTRY_TEXT");
            query.andEqual("TASK", taskid);
            query.andEqual("LANGUAGE", language);
            query.setMaxResults(1);
            mobject = documents.select(query);
            if (mobject != null)
                entry.setText((String)mobject[0].get("TEXT"));
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
        Table table;
        ExpandBar group;
        Link link;
        Set<TaskEntry> entries;
        Parameter groupcommand;
        String taskname, text;
        StyleSheet stylesheet;
        Form container = new Form(context.view, "main");
        PageControl pagecontrol = new PageControl(container);
        
        pagecontrol.add("help", PageControl.EXTERNAL);
        stylesheet = context.view.styleSheetInstance();
        stylesheet.clone(".tasksel_eb", ".expand_bar");
        stylesheet.put(".tasksel_eb", "float", "left");
        stylesheet.put(".tasksel_eb", "display", "inline");
        stylesheet.put(".tasksel_eb", "margin-top", "10px");
        stylesheet.put(".tasksel_eb", "margin-right", "10px");
        stylesheet.remove(".tasksel_eb", "padding");
        stylesheet.remove(".tasksel_eb", "margin-bottom");
        stylesheet.clone(".tasksel_table_area", ".table_area");
        stylesheet.put(".tasksel_table_area", "border-style", "none");
        
        /*
         * tarefas pré-definidas
         */
        if (lists != null) {
            for (String groupname : lists.keySet()) {
                group = new ExpandBar(container, groupname);
                group.setExpanded(true);
                group.setEnabled(false);
                group.setStyleClass("tasksel_eb");
                table = new Table(group, groupname.concat(".table"));
                table.setHeader(false);
                table.setStyleClass("tasksel_table_area");
                new TableColumn(table, "link");
                
                entries = lists.get(groupname);
                for (TaskEntry entry : entries) {
                    groupcommand = new Parameter(container, "groupcommand");
                    taskname = entry.getName();
                    text = entry.getText();
                    link = new Link(table, taskname, "grouprun");
                    link.setText((text == null)? taskname : text);
                    link.add(groupcommand, taskname);
                    new TableItem(table).add(link);
                }
            }
        }
        
        context.view.setTitle("task-selector");
    }

}
