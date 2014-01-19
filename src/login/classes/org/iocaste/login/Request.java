package org.iocaste.login;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.packagetool.common.PackageTool;
import org.iocaste.protocol.Iocaste;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.InputComponent;

public class Request {
    private static final boolean INITIALIZE = true;
    private static final String[] PACKAGES = new String[] {
        "iocaste-packagetool",
        "iocaste-tasksel",
        "iocaste-setup",
        "iocaste-search-help"
        };
    
    public static final void changesecret(Context context) {
        DataForm form = context.view.getElement("chgscrt");
        String secret = form.get("SECRET").get();
        String confirm = form.get("CONFIRM").get();
        
        if (secret.equals(confirm)) {
            new Iocaste(context.function).setUserPassword(secret);
            
            /*
             * não queremos que essa seja a página inicial
             */
            context.view.dontPushPage();
            context.view.redirect("iocaste-tasksel", "main", INITIALIZE);
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
        PackageTool pkgtool = new PackageTool(context.function);
        DataForm form = context.view.getElement("login");
        Iocaste iocaste = new Iocaste(context.function);
        ExtendedObject login = form.getObject();
        
        context.view.clearExports();
        username = login.get("USERNAME");
        secret = login.get("SECRET");
        locale = login.get("LOCALE");
        if (iocaste.login(username, secret, locale)) {
            pkgtool = new PackageTool(context.function);
            
            for (String pkgname : PACKAGES)
                if (!pkgtool.isInstalled(pkgname))
                    pkgtool.install(pkgname);
            
            context.view.export("username", username);
            if (iocaste.isInitialSecret())
                context.view.redirect("changesecretform");
            else
                context.view.redirect("iocaste-tasksel", "main", INITIALIZE);
        } else {
            context.view.message(Const.ERROR, "invalid.login");
        }
        
        input = form.get("USERNAME");
        input.set(null);
        context.view.setFocus(input);
        form.get("SECRET").set(null);
    }
}
