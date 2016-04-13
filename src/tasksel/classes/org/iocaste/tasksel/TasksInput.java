package org.iocaste.tasksel;

import java.util.Set;

import org.iocaste.appbuilder.common.AbstractViewInput;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class TasksInput extends AbstractViewInput {

    @Override
    protected void execute(PageBuilderContext context) {
        String text;
        Set<TaskEntry> entries;
        Main function = (Main)context.function;
        
        for (String name : function.groups.keySet()) {
            entries = function.groups.get(name);
            for (TaskEntry entry : entries) {
                text = entry.getText();
                if (text == null)
                    text = entry.getName();
                dbitemadd("groups", name, text, entry.getName());
            }
        }
    }

    @Override
    protected void init(PageBuilderContext context) {
        execute(context);
    }

}
