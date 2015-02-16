package org.iocaste.packagetool;

import java.util.Map;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.StyleSheet;

public class InstallStyles {

    public static final void init(
            Map<String, StyleSheet> stylesheets, State state) {
        Query query;
        ExtendedObject object;
        DocumentModel model;
        String defaultstyle = state.data.getApplicationStyle();
        Shell shell = new Shell(state.function);
        
        query = new Query("delete");
        query.setModel("APP_STYLE");
        query.andEqual("APPNAME", state.pkgname);
        state.documents.update(query);
        
        if (defaultstyle != null) {
            model = state.documents.getModel("APP_STYLE");
            object = new ExtendedObject(model);
            object.set("APPNAME", state.pkgname);
            object.set("STYLE", defaultstyle);
            state.documents.save(object);
        }
        
        for (String name : stylesheets.keySet()) {
            shell.save(name, stylesheets.get(name));
            Registry.add(name, "STYLE", state);
        }
    }
    
    public static final void uninstall(String name, Function function) {
        new Shell(function).removeStyle(name);
    }
}
