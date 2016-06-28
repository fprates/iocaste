package org.iocaste.workbench.project.view.config;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.workbench.Context;

public class ViewConfigContext {
    public Map<String, Map<String, ViewElementAttribute>> attribs;
    public Context extcontext;
    
    public ViewConfigContext() {
        attribs = new HashMap<>();
    }
}
