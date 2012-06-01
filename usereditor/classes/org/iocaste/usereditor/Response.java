package org.iocaste.usereditor;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataItem;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.ViewData;

public class Response {

    /**
     * 
     * @param view
     * @param function
     * @throws Exception
     */
    public static final void form(ViewData view, Function function)
            throws Exception {
        Button save;
        DataItem secret, confirm, username;
        ExtendedObject object;
        Container container = new Form(view, "main");
        byte mode = Common.getMode(view);
        DataForm form = new DataForm(container, "identity");
        DocumentModel model = new Documents(function).getModel("LOGIN");
        
        form.importModel(model);
        form.get("ID").setVisible(false);
        secret = form.get("SECRET");
        secret.setSecret(true);
        username = form.get("USERNAME");
        username.setEnabled(false);
        confirm = new DataItem(form, Const.TEXT_FIELD, "secret.confirm");
        confirm.setSecret(true);
        confirm.setModelItem(secret.getModelItem());
        
        save = new Button(container, "save");
        
        switch (mode) {
        case Common.CREATE:
            username.set(view.getParameter("username"));
            break;
            
        case Common.DISPLAY:
            object = view.getParameter("identity");
            form.setObject(object);
            save.setVisible(false);
            break;
            
        case Common.UPDATE:
            object = view.getParameter("identity");
            form.setObject(object);
            break;
        }
        
        view.setNavbarActionEnabled("back", true);
        view.setTitle(Common.TITLE[mode]);
    }
    
    /**
     * 
     * @param view
     * @param function
     * @throws Exception
     */
    public static final void selector(ViewData view, Function function)
            throws Exception {
        Container container = new Form(view, "main");
        DataForm form = new DataForm(container, "selection");
        
        form.importModel(new Documents(function).getModel("LOGIN"));
        for (Element element : form.getElements()) {
            if (!element.getName().equals("USERNAME")) {
                element.setVisible(false);
                continue;
            }

            view.setFocus(element);
            ((InputComponent)element).setObligatory(true);
        }
        
        new Button(container, "create");
        new Button(container, "display");
        new Button(container, "update");
        
        view.setNavbarActionEnabled("back", true);
        view.setTitle("user-selection");
    }
}
