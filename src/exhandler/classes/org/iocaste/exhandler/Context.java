package org.iocaste.exhandler;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.ExtendedContext;
import org.iocaste.shell.common.View;

public class Context implements ExtendedContext {
    public Map<String, String> messages;
    public Exception ex;
    public View exview;
    
    public Context() {
        messages = new HashMap<>();
    }   
}
