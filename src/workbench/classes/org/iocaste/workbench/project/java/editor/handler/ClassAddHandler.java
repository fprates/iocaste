package org.iocaste.workbench.project.java.editor.handler;

import org.iocaste.workbench.project.java.editor.ClassEditorContext;

public class ClassAddHandler extends AbstractClassHandler {
    
    public ClassAddHandler(ClassEditorContext context) {
        super("class-add", context);
    }
    
    @Override
    public final void execute() {
        String fullname = getFullName();
        String projectname = context.extcontext.project.getstKey();
        
        context.classobject = context.document.instance("class", fullname);
        context.classobject.set("PROJECT", projectname);
        context.classobject.set("PACKAGE", packagename);
        context.classobject.set("NAME", classname);
        context.classobject.set("FULL_NAME", fullname);
        context.source = null;
    }
}
