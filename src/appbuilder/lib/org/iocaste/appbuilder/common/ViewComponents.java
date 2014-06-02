package org.iocaste.appbuilder.common;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.shell.common.TableTool;

public class ViewComponents {
    public Map<String, TableTool> tabletools;
    
    public ViewComponents() {
        tabletools = new HashMap<>();
    }
}
