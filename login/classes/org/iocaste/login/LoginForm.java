package org.iocaste.login;

import org.iocaste.protocol.Iocaste;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.ControlData;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.DataFormItem;
import org.iocaste.shell.common.Form;
import org.iocaste.shell.common.MessageSource;
import org.iocaste.shell.common.ViewData;

public class LoginForm extends AbstractPage {
    
    /**
     * 
     * @param view
     */
    public final void authentic(ViewData view) {
        Container form = new Form(null, "main");
        DataForm loginform = new DataForm(form, "login");
        
        loginform.addAction("connect");
        view.setFocus(new DataFormItem(loginform, Const.TEXT_FIELD, "username"));
        new DataFormItem(loginform, Const.PASSWORD, "secret");
        
        view.setMessages(new MessageSource("/message.properties"));
        view.setTitle("authentic");
        view.addContainer(form);
    }
    
    /**
     * 
     * @param controldata
     * @param view
     * @throws Exception
     */
    public final void connect(ControlData controldata, ViewData view) throws Exception {
        DataForm form = (DataForm)view.getElement("login");
        Iocaste iocaste = new Iocaste(this);
        Login login = new Login();
        
        form.exportTo(login);
        
        if (iocaste.login(login.getUsername(), login.getSecret()))
            controldata.redirect("iocaste-tasksel", "main");
        else
            controldata.message(Const.ERROR, "invalid.login");
    }
}
