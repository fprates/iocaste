package org.iocaste.workbench.shell.source;

import org.iocaste.documents.common.Documents;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.PageControl;
import org.iocaste.texteditor.common.TextEditorTool;
import org.iocaste.workbench.Context;

public class Editor {

    public static final void output(Context context) {
        Form container = new Form(context.view, "main");
        PageControl pagecontrol = new PageControl(container);
        
        context.tetool = new TextEditorTool(context);
        context.editor = context.tetool.instance(container, "editor");
        context.tetool.load(context.editor, "WB_SOURCES", context.sourceid);
        
        pagecontrol.add("back");
        pagecontrol.add("save", PageControl.REQUEST);
        
        context.view.setFocus(context.editor.getElement());
        context.view.setTitle(context.fullsourcename);
    }
    
    public static final void save(Context context) {
        Documents documents = new Documents(context.function);
        
        if (context.editormode == Context.NEW)
            documents.save(context.sources.get(context.fullsourcename));
        
        context.tetool.commit(context.editor, context.sourceid);
        context.tetool.update(context.editor, "WB_SOURCES");
        context.view.message(Const.STATUS, "project.saved");
    }
}
