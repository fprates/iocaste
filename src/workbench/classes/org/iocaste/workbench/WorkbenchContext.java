package org.iocaste.workbench;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.cmodelviewer.Context;
import org.iocaste.documents.common.ExtendedObject;

public class WorkbenchContext extends Context {
    public Map<String, ExtendedObject[]> models;
    
    public WorkbenchContext() {
        models = new HashMap<>();
    }
}