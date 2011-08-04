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
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.AbstractForm#buildViews()
     */
    @Override
    protected final void buildViews() {
        ViewData view = getViewInstance("authentic");
        
        messages = new MessageSource("/message.properties");

        form = new Form(null, "login");
        form.addAction("connect");
        form.setMessageSource(messages);
        
        view.setFocus(new FormItem(form, "username", Const.TEXT_FIELD));
        new FormItem(form, "secret", Const.PASSWORD);
        
        view.setTitle("authentic");
        view.setContainer(form);
    }
    
    /**
     * 
     * @throws Exception
     */
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
