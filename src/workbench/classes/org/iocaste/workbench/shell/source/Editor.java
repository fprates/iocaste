package org.iocaste.workbench.shell.source;

import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Form;
import org.iocaste.texteditor.common.TextEditorTool;
import org.iocaste.workbench.Common;
import org.iocaste.workbench.Context;

public class Editor {
//    public static final void output(Context context) {
//        Form container = new Form(context.view, "main");
//        PageControl pagecontrol = new PageControl(container);
//        
//        context.tetool = new TextEditorTool(context);
//        context.editor = context.tetool.instance(container, "editor");
//        context.editor.setWidth(80);
//        context.editor.setHeight(40);
//        context.tetool.load(context.editor, context.projectsourceobj,
//                context.projectsourceid);
//        
//        pagecontrol.add("back");
//        pagecontrol.add("save", PageControl.REQUEST);
//        
//        context.view.setFocus(context.editor.getElement());
//        context.view.setTitle(context.projectfullsourcename);
//    }
//    
//    public static final void save(Context context) {
//        Common.register(context);
//        context.tetool.commit(context.editor, context.projectsourceid);
//        context.tetool.update(context.editor, context.projectsourceobj);
//        context.view.message(Const.STATUS, "project.saved");
//    }
}
