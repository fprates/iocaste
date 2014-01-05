package org.iocaste.workbench;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.shell.common.PageContext;
import org.iocaste.texteditor.common.TextEditorTool;

public class Context extends PageContext {
    public String project, fullsourcename, sourcename;
    public long packageid;
    public TextEditorTool tetool;
    public Map<String, Long> sources;
    
    public Context() {
        sources = new HashMap<>();
    }
}
