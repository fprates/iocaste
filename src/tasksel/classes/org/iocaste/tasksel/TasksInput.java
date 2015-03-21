package org.iocaste.tasksel;

import java.util.Set;

import org.iocaste.appbuilder.common.AbstractViewInput;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class TasksInput extends AbstractViewInput {

    @Override
    protected void execute(PageBuilderContext context) {
        String text;
        Set<TaskEntry> entries;
        Context extcontext = getExtendedContext();
        
        for (String name : extcontext.groups.keySet()) {
            entries = extcontext.groups.get(name);
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
