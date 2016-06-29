package org.iocaste.workbench.project.java.editor;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.Context;

public class ClassEditorSave extends AbstractActionHandler {

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        ExtendedObject _class;
        String project, fullname;
        Context extcontext = getExtendedContext();

        extcontext.classeditor.document = save(extcontext.classeditor.document);

        _class = extcontext.classeditor.classobject;
        project = _class.getst("PROJECT");
        fullname = _class.getst("FULL_NAME");
        if (_class.getst("CLASS_ID") == null)
            textcreate(project);
        
        _class = extcontext.classeditor.document.getItemsMap("class").
                get(fullname);
        textsave(project, _class.getst("CLASS_ID"), textget("source"));
        message(Const.STATUS, "java.class.saved");
    }
    
}