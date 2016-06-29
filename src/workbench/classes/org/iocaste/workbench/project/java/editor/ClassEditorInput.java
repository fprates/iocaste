package org.iocaste.workbench.project.java.editor;

import org.iocaste.appbuilder.common.AbstractViewInput;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.workbench.Context;

public class ClassEditorInput extends AbstractViewInput {

    @Override
    protected void execute(PageBuilderContext context) {
        Context extcontext = getExtendedContext();
        
        dfset("head", extcontext.classeditor.classobject);
        texteditorset("source", extcontext.classeditor.source);
    }

    @Override
    protected void init(PageBuilderContext context) {
        execute(context);
    }
    
}