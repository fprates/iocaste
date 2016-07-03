package org.iocaste.workbench;

import java.util.Map;

import org.iocaste.workbench.project.viewer.ViewerUpdate;

public class ActionContext {
    public Map<String, CommandArgument> arguments;
    public String name;
    public AbstractCommand handler;
    public boolean mainrestart, back;
    public ViewerUpdate updateviewer;
}