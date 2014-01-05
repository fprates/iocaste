package org.iocaste.workbench.shell;

import java.util.HashMap;
import java.util.Map;

public class Common {

    public static final Map<String, String> getParameters(
            String[] tokens, String... names) {
        String absname;
        Map<String, String> parameters = new HashMap<>();
        
        for (String name : names) {
            absname = new StringBuilder(name).append('=').toString();
            for (int i = 0; i < tokens.length; i++) {
                if (!tokens[i].startsWith(absname))
                    continue;
                parameters.put(name, tokens[i].split("=")[1]);
                break;
            }
        }
        
        return parameters;
    }
}
