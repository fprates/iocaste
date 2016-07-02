package org.iocaste.workbench.project.java;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.Context;
import org.iocaste.workbench.project.java.editor.handler.ClassHandler;

public class ClassEditorCall extends AbstractCommand {
    private String op;
    public ClassEditorCall(String op, Context extcontext) {
        super(op, extcontext);
        required("package", null);
        required("class", null);
        this.op = op;
    }
    
    @Override
    protected Object entry(PageBuilderContext context) {
        String packagename, classname;
        Context extcontext;
        ClassHandler handler;
        
        packagename = parameters.get("package");
        extcontext = getExtendedContext();
        extcontext.classeditor.document = extcontext.project.
                getDocumentsMap("class").get(packagename);
        if (extcontext.classeditor.document == null) {
            message(Const.ERROR, "invalid.package");
            return null;
        }

        classname = parameters.get("class");
        handler = extcontext.classeditor.handlers.get(op);
        handler.setPackage(packagename);
        handler.setClassName(classname);
        handler.execute();
        if (extcontext.classeditor.classobject == null) {
            message(Const.ERROR, "invalid.class");
            return null;
        }
        
        init("class-editor", extcontext);
        redirect("class-editor");
        return null;
    }

}
