package org.iocaste.workbench.project;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.workbench.ProjectPackage;

public class ProjectData {
    long id;
    public ExtendedObject header;
    public String name, dir, entryclass, service, defaultpackage;
    public String currentsource;
    public Map<String, ProjectPackage> packages;
    
    public ProjectData() {
        packages = new HashMap<>();
    }
}
