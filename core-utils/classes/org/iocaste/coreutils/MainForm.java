package org.iocaste.coreutils;

import java.sql.BatchUpdateException;

import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.user.User;
import org.iocaste.shell.common.AbstractForm;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.FormItem;
import org.iocaste.shell.common.Menu;
import org.iocaste.shell.common.MenuItem;
import org.iocaste.shell.common.MessageSource;
import org.iocaste.shell.common.ViewData;

public class MainForm extends AbstractForm {
    private Form form;
    private Menu mainMenu;
    private MessageSource messages;

    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.AbstractForm#buildViews()
     */
    @Override
    protected final void buildViews() {
        ViewData view = getViewInstance("main");
        
        messages = new MessageSource("/messages.properties");
        mainMenu = new Menu(null, "run");
        new MenuItem(mainMenu, "user.add", "add");
        new MenuItem(mainMenu, "user.change", "change");
        view.setContainer(mainMenu);
        
        view = getViewInstance("form");
        
        form = new Form(null, "user");
        form.addAction("save");
        view.setFocus(new FormItem(form, "username", Const.TEXT_FIELD));
        new FormItem(form, "secret", Const.PASSWORD);
        
        view.setMessages(messages);
        view.setContainer(form);
    }
    
    /**
     * 
     */
    public final void run() {
        String value = mainMenu.getParameter().getValue();
        
        if (value.equals("add"))
            redirect("iocaste-core-utils", "form");
    }
    
    /**
     * 
     * @throws Exception
     */
    public final void save() throws Exception {
        Iocaste iocaste = new Iocaste(this);
        User user = new User();
        
        form.exportTo(user);
        
        try {
            iocaste.createUser(user);
        } catch (Exception e) {
            message(messages, Const.ERROR, "user.already.exists");
        }
    }
}
