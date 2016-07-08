package org.iocaste.workbench.project.java;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.ActionContext;
import org.iocaste.workbench.Context;

public class ClassRemove extends AbstractCommand {
    
    public ClassRemove(Context extcontext) {
        super("class-remove", extcontext);
        ActionContext actionctx;
        
        required("package", "PACKAGE");
        required("class", "CLASS");
        actionctx = getActionContext();
        actionctx.updateviewer = new ClassItemUpdate(extcontext);
    }
    
    @Override
    protected Object entry(PageBuilderContext context) {
        ExtendedObject object;
        String packagename, classname, project, classid;
        Context extcontext;
        
        packagename = getst("package");
        extcontext = getExtendedContext();
        extcontext.classeditor.document = extcontext.project.
                getDocumentsMap("class").get(packagename);
        if (extcontext.classeditor.document == null) {
            message(Const.ERROR, "invalid.package");
            return null;
        }

        classname = new StringBuilder(packagename).append(".").
                append(getst("class")).toString();
        object = extcontext.classeditor.document.getItemsMap("class").
                get(classname);
        if (object == null) {
            message(Const.ERROR, "invalid.class");
            return null;
        }
        
        extcontext.classeditor.document.remove(object);
        save(extcontext.classeditor.document);
        
        project = extcontext.project.getstKey();
        classid = object.getst("CLASS_ID");
        textremove(project, classid);
        
        message(Const.STATUS, "class.removed");
        return null;
    }

}
