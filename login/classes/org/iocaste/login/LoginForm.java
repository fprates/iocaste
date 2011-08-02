package org.iocaste.login;

import org.iocaste.protocol.Iocaste;
import org.iocaste.shell.common.AbstractForm;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.FormItem;
import org.iocaste.shell.common.MessageSource;
import org.iocaste.shell.common.ViewData;

public class LoginForm extends AbstractForm {
    private MessageSource messages;
    private Form form;
    
    public LoginForm() {
        ViewData view = new ViewData();
        
        messages = new MessageSource("/message.properties");

        form = new Form(null, "login");
        form.addAction("connect");
        form.setMessageSource(messages);
        
        new FormItem(form, "username", Const.TEXT_FIELD);
        new FormItem(form, "secret", Const.PASSWORD);
        
        view.setTitle("authentic");
        view.setContainer(form);
        
        addView("authentic", view);
    }
    
    public final void connect() throws Exception {
        Iocaste iocaste = new Iocaste(this);
        Login login = new Login();
        
        form.exportTo(login);
        
        if (iocaste.login(login.getUsername(), login.getSecret()))
            redirect("iocaste-tasksel", "main");
        else
            message(messages, Const.ERROR, "invalid.login");
    }
}
