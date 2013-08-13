package org.iocaste.workbench;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.documents.common.ExtendedObject;

public class Project {
    long id;
    public ExtendedObject header;
    public String name, dir, entryclass, service;
    public Map<String, ProjectPackage> packages;
    
    public Project() {
        packages = new HashMap<>();
    }
}
