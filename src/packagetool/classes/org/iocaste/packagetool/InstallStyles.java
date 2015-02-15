package org.iocaste.packagetool;

import java.util.Map;

import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.StyleSheet;

public class InstallStyles {

    public static final void init(
            Map<String, StyleSheet> stylesheets, State state) {
        Shell shell = new Shell(state.function);
        
        for (String name : stylesheets.keySet()) {
            shell.save(name, stylesheets.get(name));
            Registry.add(name, "STYLE", state);
        }
    }
    
    public static final void uninstall(String name, Function function) {
        new Shell(function).removeStyle(name);
    }
}
