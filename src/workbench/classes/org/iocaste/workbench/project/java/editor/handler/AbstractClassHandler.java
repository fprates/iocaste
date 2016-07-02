package org.iocaste.workbench.project.java.editor.handler;

import java.util.Map;

import org.iocaste.texteditor.common.TextEditorTool;
import org.iocaste.workbench.project.java.editor.ClassEditorContext;

public abstract class AbstractClassHandler implements ClassHandler {
    protected ClassEditorContext context;
    protected String packagename;
    protected String classname;
    
    public AbstractClassHandler(String action, ClassEditorContext context) {
        this.context = context;
        context.handlers.put(action, this);
    }
    
    protected final String getFullName() {
        return new StringBuilder(packagename).append(".").
        append(classname).toString();
    }
    
    protected final Map<String, String> getText(String id, String page) {
        return new TextEditorTool(context.context).get(id, page);
    }
    
    @Override
    public final void setPackage(String packagename) {
        this.packagename = packagename;
    }
    
    @Override
    public final void setClassName(String classname) {
        this.classname = classname;
    }
}