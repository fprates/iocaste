package org.iocaste.workbench.project.java.editor.handler;

import java.util.Map;

import org.iocaste.workbench.project.java.editor.ClassEditorContext;

public class ClassEditHandler extends AbstractClassHandler {
    
    public ClassEditHandler(ClassEditorContext context) {
        super("class-edit", context);
    }
    
    @Override
    public final void execute() {
        Map<String, String> texts;
        String classid;
        String fullname = getFullName();
        String projectname = context.extcontext.project.getstKey();
        
        context.classobject = context.document.getItemsMap("class").
                get(fullname);

        classid = context.classobject.getst("CLASS_ID");
        texts = getText(projectname, classid);
        for (String key : texts.keySet()) {
            context.source = texts.get(key);
            break;
        }
    }
}
