package org.iocaste.login;

import org.iocaste.protocol.Iocaste;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.ControlData;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.FormItem;
import org.iocaste.shell.common.MessageSource;
import org.iocaste.shell.common.ViewData;

public class LoginForm extends AbstractPage {
    
    /**
     * 
     * @param view
     */
    public final void authentic(ViewData view) {
        Form form = new Form(null, "login");
        form.addAction("connect");
        
        view.setFocus(new FormItem(form, Const.TEXT_FIELD, "username"));
        new FormItem(form, Const.PASSWORD, "secret");
        
        view.setMessages(new MessageSource("/message.properties"));
        view.setTitle("authentic");
        view.setContainer(form);
    }
    
    /**
     * 
     * @param controldata
     * @param view
     * @throws Exception
     */
    public final void connect(ControlData controldata, ViewData view) throws Exception {
        Form form = (Form)view.getElement("login");
        Iocaste iocaste = new Iocaste(this);
        Login login = new Login();
        
        form.exportTo(login);
        
        if (iocaste.login(login.getUsername(), login.getSecret()))
            controldata.redirect("iocaste-tasksel", "main");
        else
            controldata.message(Const.ERROR, "invalid.login");
    }
}
