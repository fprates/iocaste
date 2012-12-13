package org.iocaste.workbench;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.shell.common.View;

public class Project {
    public DocumentModel model;
    public String name, source, dir, classfile, viewname;
    public boolean created;
    public Map<String, View> views;
    public Map<String, Source> sources;
    
    public Project() {
        views = new HashMap<>();
        sources = new HashMap<>();
    }
}
