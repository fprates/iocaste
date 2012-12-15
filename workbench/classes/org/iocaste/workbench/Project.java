package org.iocaste.workbench;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iocaste.documents.common.ExtendedObject;

public class Project {
    public ExtendedObject header;
    public Map<String, ProjectPackage> packages;
    
    public Project() {
        packages = new HashMap<>();
    }
}
