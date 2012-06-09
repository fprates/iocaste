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
import org.iocaste.shell.common.PageControl;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.TabbedPane;
import org.iocaste.shell.common.TabbedPaneItem;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.View;

public class Response {

    /**
     * 
     * @param view
     * @param function
     * @throws Exception
     */
    public static final void form(View view, Function function)
            throws Exception {
        Container profilecnt;
        Table profiles;
        TabbedPaneItem tabitem;
        Button save, addprofile, removeprofile;
        DataItem secret, confirm, username;
        ExtendedObject object;
        ExtendedObject[] oprofiles;
        Form container = new Form(view, "main");
        PageControl pagecontrol = new PageControl(container);
        byte mode = Common.getMode(view);
        TabbedPane tabs = new TabbedPane(container, "tabs");
        DataForm form = new DataForm(tabs, "identity");
        Documents documents = new Documents(function);
        DocumentModel model = documents.getModel("LOGIN");

        pagecontrol.add("home");
        pagecontrol.add("back");
        
        /*
         * identificação
         */
        form.importModel(model);
        form.get("ID").setVisible(false);
        secret = form.get("SECRET");
        secret.setSecret(true);
        username = form.get("USERNAME");
        username.setEnabled(false);
        confirm = new DataItem(form, Const.TEXT_FIELD, "secret.confirm");
        confirm.setSecret(true);
        confirm.setModelItem(secret.getModelItem());
        
        if (mode == Common.DISPLAY) {
            secret.setEnabled(false);
            confirm.setEnabled(false);
        }
        
        tabitem = new TabbedPaneItem(tabs, "idtab");
        tabitem.setContainer(form);
        
        /*
         * perfis
         */
        profilecnt = new StandardContainer(tabs, "profilecnt");
        
        addprofile = new Button(profilecnt, "addprofile");
        removeprofile = new Button(profilecnt, "removeprofile");
        
        model = documents.getModel("USER_AUTHORITY");
        profiles = new Table(profilecnt, "profiles");
        profiles.importModel(model);
        
        for (TableColumn column : profiles.getColumns())
            if (!column.getName().equals("PROFILE"))
                column.setVisible(false);
        
        tabitem = new TabbedPaneItem(tabs, "profiletab");
        tabitem.setContainer(profilecnt);
        
        save = new Button(container, "save");
        
        switch (mode) {
        case Common.CREATE:
            addprofile.setVisible(true);
            removeprofile.setEnabled(false);
            profiles.setMark(true);
            username.set(view.getParameter("username"));
            break;
            
        case Common.DISPLAY:
            addprofile.setVisible(false);
            removeprofile.setVisible(false);
            profiles.setMark(false);
            object = view.getParameter("identity");
            form.setObject(object);
            save.setVisible(false);
            
            oprofiles = view.getParameter("profiles");
            if (oprofiles != null)
                for (ExtendedObject oprofile : oprofiles)
                    Common.insertItem(profiles, oprofile, mode);
            
            break;
            
        case Common.UPDATE:
            addprofile.setVisible(true);
            profiles.setMark(true);
            object = view.getParameter("identity");
            form.setObject(object);
            
            oprofiles = view.getParameter("profiles");
            if (oprofiles != null)
                for (ExtendedObject oprofile : oprofiles)
                    Common.insertItem(profiles, oprofile, mode);
            
            break;
        }
        
        view.setFocus(secret);
        view.setTitle(Common.TITLE[mode]);
    }
    
    /**
     * 
     * @param view
     * @param function
     * @throws Exception
     */
    public static final void selector(View view, Function function)
            throws Exception {
        Form container = new Form(view, "main");
        PageControl pagecontrol = new PageControl(container);
        DataForm form = new DataForm(container, "selection");
        
        pagecontrol.add("home");
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
        
        view.setTitle("user-selection");
    }
}
