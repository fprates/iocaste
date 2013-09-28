package org.iocaste.gconfigview;

import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.PageControl;

public class Response {

    /**
     * 
     * @param view
     * @param mode
     */
    public static final void configform(Context context) {
        InputComponent input;
        String name, value;
        int type;
        Form container = new Form(context.view, "container");
        PageControl pagecontrol = new PageControl(container);
        DataForm form = new DataForm(container, "package.config");
        
        for (ExtendedObject object : context.objects) {
            name = object.getValue("NAME");
            type = object.geti("TYPE");
            value = object.getValue("VALUE");
            
            switch (type) {
            case DataType.BOOLEAN:
                input = new DataItem(form, Const.CHECKBOX, name);
                input.setSelected(Boolean.parseBoolean(value));
                break;
            default:
                input = new DataItem(form, Const.TEXT_FIELD, name);
                input.setLength(256);
                input.setVisibleLength(20);
                input.set(value);
                break;
            }
            
            input.setEnabled(context.mode == Context.EDIT);
        }
        
        if (context.mode == Context.EDIT)
            new Button(container, "save");
        
        pagecontrol.add("home");
        pagecontrol.add("back");
        context.view.setTitle(Context.TITLES[context.mode]);
    }
    
    /**
     * @param args
     */
    public static final void main(Context context) {
        InputComponent input;
        Form container = new Form(context.view, "container");
        PageControl pagecontrol = new PageControl(container);
        DataForm form = new DataForm(container, "package");
        
        pagecontrol.add("home");
        form.importModel(new Documents(context.function).
                getModel("GLOBAL_CONFIG"));
        for (Element element : form.getElements())
            element.setVisible(false);
        
        input = form.get("NAME");
        input.setObligatory(true);
        input.setVisible(true);
        
        context.view.setFocus(input);
        context.view.setTitle(Context.TITLES[Context.SELECT]);
        
        new Button(container, "display");
        new Button(container, "edit");
    }

}
