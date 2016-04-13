package org.iocaste.exhandler;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.AbstractExtendedContext;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.View;

public class Context extends AbstractExtendedContext {
    public Map<String, String> messages;
    public Exception ex;
    public View exview;
    
    public Context(PageBuilderContext context) {
        super(context);
        messages = new HashMap<>();
    }   
}
