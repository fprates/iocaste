package org.iocaste.gconfigview;

import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.PageControl;
import org.iocaste.shell.common.View;

public class Response {

    public static final void configform(View view) {
        InputComponent input;
        String name, value;
        int type;
        Form container = new Form(view, "container");
        PageControl pagecontrol = new PageControl(container);
        DataForm form = new DataForm(container, "package.config");
        ExtendedObject[] objects = view.getParameter("objects");
        
        for (ExtendedObject object : objects) {
            name = object.getValue("NAME");
            type = object.getValue("TYPE");
            value = object.getValue("VALUE");
            
            switch (type) {
            case DataType.BOOLEAN:
                input = new DataItem(form, Const.CHECKBOX, name);
                input.setSelected(Boolean.parseBoolean(value));
                break;
            default:
                input = new DataItem(form, Const.TEXT_FIELD, name);
                input.set(value);
                break;
            }
            
            input.setEnabled(Common.getMode(view) == Common.EDIT);
        }
        
        pagecontrol.add("home");
        pagecontrol.add("back");
    }
    
    /**
     * @param args
     */
    public static final void main(View view, Function function) {
        InputComponent input;
        Form container = new Form(view, "container");
        PageControl pagecontrol = new PageControl(container);
        DataForm form = new DataForm(container, "package");
        
        pagecontrol.add("home");
        form.importModel(new Documents(function).getModel("GLOBAL_CONFIG"));
        for (Element element : form.getElements())
            element.setVisible(false);
        
        input = form.get("NAME");
        input.setObligatory(true);
        input.setVisible(true);
        view.setFocus(input);
        
        new Button(container, "display");
        new Button(container, "edit");
    }

}
