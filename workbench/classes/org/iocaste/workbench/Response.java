package org.iocaste.workbench;

import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.PageControl;

public class Response {
    
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
