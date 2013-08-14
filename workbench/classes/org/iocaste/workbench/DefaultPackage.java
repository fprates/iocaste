package org.iocaste.workbench;

import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.PageControl;

public class DefaultPackage {

    public static final void render(Context context) {
        Form container = new Form(context.view, "main");
        PageControl pagecontrol = new PageControl(container);
        DataForm form = new DataForm(container, "defaultpackage");
        
        pagecontrol.add("back");
        form.importModel(context.packagemodel);
        for (Element element : form.getElements())
            if (!element.getName().equals("NAME")) {
                element.setVisible(false);
            } else {
                ((InputComponent)element).setObligatory(true);
                context.view.setFocus(element);
            }
        
        new Button(container, "createproject").setSubmit(true);
    }
}
