package org.iocaste.coreutils;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.user.User;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.ControlData;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.Menu;
import org.iocaste.shell.common.MenuItem;
import org.iocaste.shell.common.MessageSource;
import org.iocaste.shell.common.ViewData;

public class MainForm extends AbstractPage {

    /**
     * 
     * @param view
     * @throws Exception
     */
    public final void main(ViewData view) throws Exception {
        Menu mainMenu = new Menu(null, "run");
        
        new MenuItem(mainMenu, "user.add", "add");
        new MenuItem(mainMenu, "user.change", "change");
        view.setMessages(new MessageSource("/messages.properties"));
        view.setContainer(mainMenu);
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public final void form(ViewData view) throws Exception {
        DataForm form = new DataForm(null, "user");
        Documents documents = new Documents(this);
        DocumentModel document = documents.getModel("login");
        
        form.addAction("save");
        form.importModel(document);
        
        view.setMessages(new MessageSource("/messages.properties"));
        view.setContainer(form);
    }
    
    /**
     * 
     * @param controldata
     * @param view
     */
    public final void run(ControlData controldata, ViewData view) {
        Menu mainmenu= (Menu)view.getElement("run");
        String value = mainmenu.getParameter().getValue();
        
        if (value.equals("add"))
            controldata.redirect("iocaste-core-utils", "form");
    }
    
    /**
     * 
     * @throws Exception
     */
    public final void save(ControlData controldata, ViewData view)
            throws Exception {
        DataForm form = (DataForm)view.getElement("user");
        Iocaste iocaste = new Iocaste(this);
        User user = new User();
        
        form.exportTo(user);
        
        try {
            iocaste.createUser(user);
        } catch (Exception e) {
            controldata.message(Const.ERROR, "user.already.exists");
        }
    }
}
