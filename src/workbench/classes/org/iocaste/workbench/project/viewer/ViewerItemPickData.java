package org.iocaste.workbench.project.viewer;

import java.util.Map;

public class ViewerItemPickData {
    public String command, redirect, pickname, value;
    public ItemLoader loader;
    
    public ViewerItemPickData(
            String name, Map<String, ViewerItemPickData> pickdata) {
        pickdata.put(name, this);
    }
}