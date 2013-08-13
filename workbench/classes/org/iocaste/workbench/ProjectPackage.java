package org.iocaste.workbench;

import java.util.HashMap;
import java.util.Map;

public class ProjectPackage {
    public Map<String, Source> sources;
    
    public ProjectPackage() {
        sources = new HashMap<>();
    }
}
