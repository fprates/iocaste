package org.iocaste.login;

import org.iocaste.protocol.Iocaste;
import org.iocaste.shell.common.AbstractForm;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.FormItem;
import org.iocaste.shell.common.ViewData;

public class LoginForm extends AbstractForm {
    public LoginForm() {
        ViewData view = new ViewData();
        Form form = new Form(null);
        
        form.setAction("connect");
        
        new FormItem(form, "username", Const.TEXT_FIELD);
        new FormItem(form, "secret", Const.PASSWORD);
        
        view.setTitle("authentic");
        view.setContainer(form);
        
        addView("authentic", view);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.AbstractForm#entry(java.lang.String)
     */
    @Override
    protected final void entry(String action) throws Exception {
        Iocaste iocaste = new Iocaste(this);
        String username = getString("username");
        String secret = getString("secret");
        
        if (iocaste.login(username, secret))
            redirect("iocaste-tasksel", null);
        else
            message(Const.ERROR, "invalid.login");
    }

    
}
