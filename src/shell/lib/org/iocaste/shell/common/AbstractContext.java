package org.iocaste.shell.common;

import java.util.Map;

public abstract class AbstractContext {
    public View view;
    public AbstractPage function;
    public String action, control;
    public Map<String, Map<String, String>> appbuildersheet;
}
