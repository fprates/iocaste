package org.iocaste.workbench;

import java.util.Map;

public class ActionContext {
    public Map<String, CommandArgument> arguments;
    public String name;
    public AbstractCommand handler;
}