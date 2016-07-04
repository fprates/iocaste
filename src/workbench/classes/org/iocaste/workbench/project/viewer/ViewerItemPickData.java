package org.iocaste.workbench.project.viewer;

import java.util.Map;

public class ViewerItemPickData {
    public String items, command, redirect;
    public ViewerItemLoader loader;
    
    public ViewerItemPickData(
            String name, Map<String, ViewerItemPickData> pickdata) {
        pickdata.put(name, this);
    }
}