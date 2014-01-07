package org.iocaste.workbench.shell.source;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
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
        context.tetool.load(context.editor, context.sourceobj,
                context.sourceid);
        
        pagecontrol.add("back");
        pagecontrol.add("save", PageControl.REQUEST);
        
        context.view.setFocus(context.editor.getElement());
        context.view.setTitle(context.fullsourcename);
    }
    
    public static final void save(Context context) {
        Query query;
        ExtendedObject object;
        Documents documents = new Documents(context.function);
        
        if (context.editormode == Context.NEW) {
            object = context.sources.get(context.fullsourcename);
            documents.save(object);
            
            query = new Query("update");
            query.values("SOURCE_ID", object.getl("SOURCE_ID"));
            query.setModel("WB_PROJECT");
            query.andEqual("PROJECT_NAME", context.project);
            documents.update(query);
        }
        
        context.tetool.commit(context.editor, context.sourceid);
        context.tetool.update(context.editor, context.sourceobj);
        context.view.message(Const.STATUS, "project.saved");
    }
}
