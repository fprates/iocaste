package org.iocaste.workbench.project.java;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.Context;

public class ClassEditorCall extends AbstractCommand {

    public ClassEditorCall() {
        required("package");
        required("class");
    }
    
    @Override
    protected void execute(PageBuilderContext context) {
        String packagename, classname, fullname;
        Context extcontext;
        
        packagename = parameters.get("package");
        extcontext = getExtendedContext();
        extcontext.classeditor.document = extcontext.project.
                getDocumentsMap("class").get(packagename);
        if (extcontext.classeditor.document == null) {
            message(Const.ERROR, "invalid.package");
            return;
        }

        classname = parameters.get("class");
        fullname = new StringBuilder(packagename).append(".").
                append(classname).toString();
        
        extcontext.classeditor.classobject =
                extcontext.classeditor.document.instance("class", fullname);
        extcontext.classeditor.classobject.set(
                "PROJECT", extcontext.project.getstKey());
        extcontext.classeditor.classobject.set(
                "PACKAGE", packagename);
        extcontext.classeditor.classobject.set(
                "NAME", classname);
        extcontext.classeditor.classobject.set(
                "FULL_NAME", fullname);
        
        init("class-editor", extcontext);
        redirect("class-editor");
    }

}
