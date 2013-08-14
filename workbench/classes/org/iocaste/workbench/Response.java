package org.iocaste.workbench;

import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.PageControl;
import org.iocaste.shell.common.TextArea;

public class Response {

    public static final void editor(Context context) {
        DataForm form;
        Source source;
        InputComponent input;
        TextArea editor;
        String packagename, sourcename;
        Form container = new Form(context.view, "main");
        PageControl pagecontrol = new PageControl(container);
        
        pagecontrol.add("home");
        pagecontrol.add("back");
        
        form = new DataForm(container, "project");
        form.importModel(context.editorhdrmodel);
        form.setObject(context.project.header);
        
        input = form.get("PACKAGE");
        input.setVisibleLength(60);
        for (Element element : form.getElements())
            element.setEnabled(false);
        
        pagecontrol.add("save", PageControl.REQUEST);
        pagecontrol.add("activate", PageControl.REQUEST);
        
        packagename = context.project.header.getValue("PACKAGE");
        sourcename = context.project.header.getValue("CLASS");
        source = context.project.packages.get(packagename).
                sources.get(sourcename);
        editor = new TextArea(container, "editor");
        editor.setSize(80, 20);
        editor.set(source.code);
        context.view.setFocus(editor);
        
        new TextArea(container, "output").setEnabled(false);
        
//        context.view.setTitle(Context.TITLES[context.mode]);
    }
    
    public static final void main(Context context) {
        InputComponent input;
        DataForm form;
        Form container = new Form(context.view, "main");
        PageControl pagecontrol = new PageControl(container);
        
        pagecontrol.add("home");
        
        form = new DataForm(container, "project");
        form.importModel(context.editorhdrmodel);
        for (Element element : form.getElements())
            if (!element.getName().equals("NAME")) {
                element.setVisible(false);
            } else {
                input = (InputComponent)element;
                input.setObligatory(true);
                context.view.setFocus(input);
            }
        
        new Button(container, "loadproject");
        new Button(container, "createproject");
    }
}
