package org.iocaste.tasksel;

import java.util.Map;
import java.util.Set;

import org.iocaste.appbuilder.common.ExtendedContext;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.AbstractPage;

public class Context implements ExtendedContext {
    public PageBuilderContext context;
    public Map<String, Set<TaskEntry>> groups;
    public AbstractPage function;
    public TaskPanelPage page;
}
