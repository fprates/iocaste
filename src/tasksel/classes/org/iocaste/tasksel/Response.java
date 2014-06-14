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
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.PageControl;
import org.iocaste.shell.common.StandardContainer;
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
            AbstractContext context) {
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

    public static final Map<String, Set<TaskEntry>> init(AbstractContext context) {
        return getLists(context);
    }
    
    /**
     * 
     * @param lists
     * @param context
     */
    public static final void main(Map<String, Set<TaskEntry>> lists,
            AbstractContext context) {
        short i;
        StandardContainer groups;
        Table table;
        ExpandBar group;
        Link link;
        Set<TaskEntry> entries;
        String taskname, text;
        StyleSheet stylesheet;
        Form container = new Form(context.view, "main");
        PageControl pagecontrol = new PageControl(container);
        
        pagecontrol.add("help", PageControl.EXTERNAL);
        context.view.setTitle("task-selector");
        
        /*
         * tarefas pré-definidas
         */
        if (lists == null)
            return;
        
        stylesheet = context.view.styleSheetInstance();
        stylesheet.clone(".tasksel_eb", ".eb_external");
        stylesheet.put(".tasksel_eb", "float", "left");
        stylesheet.put(".tasksel_eb", "display", "inline");
        stylesheet.put(".tasksel_eb", "margin-top", "10px");
        stylesheet.put(".tasksel_eb", "margin-right", "10px");
        stylesheet.put(".tasksel_eb", "width", "250px");
        stylesheet.clone(".tasksel_table_area", ".table_area");
        stylesheet.put(".tasksel_table_area", "border-style", "none");
        
        stylesheet.newElement(".tasksel_groups");
        stylesheet.put(".tasksel_groups", "margin", "auto");
        stylesheet.put(".tasksel_groups", "background-color", "transparent");
        stylesheet.put(".tasksel_groups", "width", "1100px");
        stylesheet.put(".tasksel_groups", "display", "table");
        
        i = 0;
        groups = null;
        for (String groupname : lists.keySet()) {
            if ((i % 4) == 0) {
                groups = new StandardContainer(container,
                        new StringBuilder("groups.").
                        append(i).toString());
                groups.setStyleClass("tasksel_groups");
            }
            i++;
            
            group = new ExpandBar(groups, groupname);
            group.setExpanded(true);
            group.setEnabled(false);
            group.setStyleClass("tasksel_eb");
            table = new Table(group, groupname.concat(".table"));
            table.setHeader(false);
            table.setStyleClass("tasksel_table_area");
            new TableColumn(table, "link");
            
            entries = lists.get(groupname);
            for (TaskEntry entry : entries) {
                taskname = entry.getName();
                text = entry.getText();
                link = new Link(table, taskname, "grouprun");
                link.setText((text == null)? taskname : text);
                link.add(container, "groupcommand", taskname);
                new TableItem(table).add(link);
            }
        }
    }

}
