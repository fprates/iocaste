package org.iocaste.workbench;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.shell.common.View;

public class ProjectPackage {
    public Map<String, View> views;
    public Map<String, Source> sources;
    
    public ProjectPackage() {
        views = new HashMap<>();
        sources = new HashMap<>();
    }
}
