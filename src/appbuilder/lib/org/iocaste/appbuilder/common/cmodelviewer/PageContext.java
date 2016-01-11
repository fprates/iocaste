package org.iocaste.appbuilder.common.cmodelviewer;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.documents.common.ExtendedObject;

public class PageContext {
    public Map<String, TableToolContextEntry> tabletools;
    public Map<String, ExtendedObject> dataforms;
    
    public PageContext() {
        tabletools = new HashMap<>();
        dataforms = new HashMap<>();
    }
}