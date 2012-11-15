package org.iocaste.login;

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
    
    /**
     * 
     * @param view
     * @param function
     * @return
     */
    public static final void connect(View view, Function function) {
        String username;
        InputComponent input;
        PackageTool pkgtool = new PackageTool(function);
        DataForm form = view.getElement("login");
        Iocaste iocaste = new Iocaste(function);
        Login login = form.getObject().newInstance();
        
        view.clearExports();
        username = login.getUsername();
        if (iocaste.login(username, login.getSecret(), login.getLocale())) {
            pkgtool = new PackageTool(function);
            
            for (String pkgname : PACKAGES)
                if (!pkgtool.isInstalled(pkgname))
                    pkgtool.install(pkgname);
            
            view.export("username", username);
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
