package org.iocaste.gconfigview;

import org.iocaste.documents.common.Documents;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.PageControl;
import org.iocaste.shell.common.View;

public class Response {

    /**
     * @param args
     */
    public static final void main(View view, Function function) {
        Form container = new Form(view, "container");
        PageControl pagecontrol = new PageControl(container);
        DataForm form = new DataForm(container, "");
        
        pagecontrol.add("home");
        form.importModel(new Documents(function).getModel("GLOBAL_CONFIG"));
        for (Element element : form.getElements())
            if (!element.getName().equals("NAME"))
                element.setVisible(false);
        
        new Button(container, "display");
        new Button(container, "edit");
    }

}
