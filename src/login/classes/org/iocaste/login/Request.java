package org.iocaste.login;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Iocaste;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.InputComponent;

public class Request {
    
    public static final void changesecret(Context context) {
        DataForm form = context.view.getElement("chgscrt");
        String secret = form.get("SECRET").get();
        String confirm = form.get("CONFIRM").get();
        
        if (secret.equals(confirm)) {
            new Iocaste(context.function).setUserPassword(secret);
            
            /*
             * não queremos que essa seja a página inicial
             */
            context.function.dontPushPage();
            context.function.exec("iocaste-tasksel", "main");
            return;
        }
        
        context.view.message(Const.ERROR, "password.mismatch");
    }
    
    /**
     * 
     * @param context
     */
    public static final void connect(Context context) {
        String username, secret, locale;
        InputComponent input;
        DataForm form = context.view.getElement("login");
        Iocaste iocaste = new Iocaste(context.function);
        ExtendedObject login = form.getObject();
        
        context.function.clearExports();
        username = login.get("USERNAME");
        secret = login.get("SECRET");
        locale = login.get("LOCALE");
        if (iocaste.login(username, secret, locale)) {
            context.function.export("username", username);
            if (iocaste.isInitialSecret())
                context.function.redirect("changesecretform");
            else
                context.function.exec("iocaste-tasksel", "main");
        } else {
            context.view.message(Const.ERROR, "invalid.login");
        }
        
        input = form.get("USERNAME");
        input.set(null);
        context.view.setFocus(input);
        form.get("SECRET").set(null);
    }
}
