package org.iocaste.workbench.shell.source;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.PageControl;
import org.iocaste.texteditor.common.TextEditor;
import org.iocaste.texteditor.common.TextEditorTool;
import org.iocaste.workbench.Context;

public class Editor {

    public static final void output(Context context) {
        Form container = new Form(context.view, "main");
        PageControl pagecontrol = new PageControl(container);
        
        context.tetool = new TextEditorTool(context);
        context.tetool.instance(container, "editor");
        pagecontrol.add("back");
        pagecontrol.add("save", PageControl.REQUEST);
    }
    
    public static final void save(Context context) {
        ExtendedObject object;
        DocumentModel model;
        long sourceid = context.sources.get(context.fullsourcename);
        Documents documents = new Documents(context.function);
//        TextEditor editor = context.view.getElement("editor");
//        
//        context.tetool.commit(editor, sourceid);
        
        model = documents.getModel("WB_SOURCE");
        object = new ExtendedObject(model);
        object.set("SOURCE_NAME", context.fullsourcename);
        object.set("PACKAGE_ID", context.packageid);
        object.set("PROJECT_NAME", context.project);
        object.set("SOURCE_ID", sourceid);
        documents.save(object);
        context.view.message(Const.STATUS, "project.saved");
    }
}
