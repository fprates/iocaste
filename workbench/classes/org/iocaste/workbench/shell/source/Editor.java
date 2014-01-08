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
        context.editor.setWidth(80);
        context.editor.setHeight(40);
        context.tetool.load(context.editor, context.projectsourceobj,
                context.projectsourceid);
        
        pagecontrol.add("back");
        pagecontrol.add("save", PageControl.REQUEST);
        
        context.view.setFocus(context.editor.getElement());
        context.view.setTitle(context.projectfullsourcename);
    }
    
    public static final void save(Context context) {
        Query query;
        ExtendedObject object;
        Documents documents = new Documents(context.function);
        
        if (context.editormode == Context.NEW) {
            object = context.projectsources.get(
                    context.projectfullsourcename);
            documents.save(object);
            
            /*
             * opcionalmente, marca fonte como default do projeto
             */
            query = new Query("update");
            query.values("SOURCE_ID", context.projectsourceid);
            if (context.projectdefsource != null)
                query.values("ENTRY_CLASS", context.projectfullsourcename);
            query.setModel("WB_PROJECT");
            query.andEqual("PROJECT_NAME", context.projectname);
            documents.update(query);
        } else {
            /*
             * opcionalmente, marca fonte como default do projeto
             */
            if (context.projectdefsource != null) {
                query = new Query("update");
                query.values("ENTRY_CLASS", context.projectfullsourcename);
                query.setModel("WB_PROJECT");
                query.andEqual("PROJECT_NAME", context.projectname);
                documents.update(query);
            }
        }
        
        context.tetool.commit(context.editor, context.projectsourceid);
        context.tetool.update(context.editor, context.projectsourceobj);
        context.view.message(Const.STATUS, "project.saved");
    }
}
