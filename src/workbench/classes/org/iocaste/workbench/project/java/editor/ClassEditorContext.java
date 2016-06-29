package org.iocaste.workbench.project.java.editor;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.workbench.Context;
import org.iocaste.workbench.project.java.editor.handler.ClassAddHandler;
import org.iocaste.workbench.project.java.editor.handler.ClassEditHandler;
import org.iocaste.workbench.project.java.editor.handler.ClassHandler;

public class ClassEditorContext {
    public ComplexDocument document;
    public ExtendedObject classobject;
    public String source;
    public Map<Byte, ClassHandler> handlers;
    public Context extcontext;
    public PageBuilderContext context;
    
    public ClassEditorContext(PageBuilderContext context, Context extcontext) {
        this.extcontext = extcontext;
        this.context = context;
        handlers = new HashMap<>();
        new ClassAddHandler(this);
        new ClassEditHandler(this);
    }
}
