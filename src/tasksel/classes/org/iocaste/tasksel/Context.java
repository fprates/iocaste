package org.iocaste.tasksel;

import java.util.Map;
import java.util.Set;

import org.iocaste.appbuilder.common.ExtendedContext;

public class Context implements ExtendedContext {
    public Map<String, Set<TaskEntry>> groups;
}
