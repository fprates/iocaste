package org.iocaste.login;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.packagetool.common.PackageTool;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Iocaste;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.View;

public class Request {
    private static final String[] PACKAGES = new String[] {
        "iocaste-packagetool",
        "iocaste-tasksel",
        "iocaste-setup",
        "iocaste-search-help"
        };
    
    public static final void changesecret(View view, Context context) {
        DataForm form = view.getElement("chgscrt");
        String secret = form.get("SECRET").get();
        String confirm = form.get("CONFIRM").get();
        
        if (secret.equals(confirm)) {
            new Iocaste(context.function).setUserPassword(secret);
            view.dontPushPage(); // não queremos que essa seja a página inicial
            view.redirect("iocaste-tasksel", "main");
            return;
        }
        
        view.message(Const.ERROR, "password.mismatch");
    }
    
    /**
     * 
     * @param view
     * @param function
     * @return
     */
    public static final void connect(View view, Function function) {
        String username, secret, locale;
        InputComponent input;
        PackageTool pkgtool = new PackageTool(function);
        DataForm form = view.getElement("login");
        Iocaste iocaste = new Iocaste(function);
        ExtendedObject login = form.getObject();
        
        view.clearExports();
        username = login.getValue("USERNAME");
        secret = login.getValue("SECRET");
        locale = login.getValue("LOCALE");
        if (iocaste.login(username, secret, locale)) {
            pkgtool = new PackageTool(function);
            
            for (String pkgname : PACKAGES)
                if (!pkgtool.isInstalled(pkgname))
                    pkgtool.install(pkgname);
            
            view.export("username", username);
            if (iocaste.isInitialSecret())
                view.redirect("changesecretform");
            else
                view.redirect("iocaste-tasksel", "main");
        } else {
            view.message(Const.ERROR, "invalid.login");
        }
        
        input = form.get("USERNAME");
        view.setFocus(input);
        input.set(null);
        form.get("SECRET").set(null);
    }
}
