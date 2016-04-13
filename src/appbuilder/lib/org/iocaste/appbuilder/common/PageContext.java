package org.iocaste.appbuilder.common;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.cmodelviewer.TableToolContextEntry;
import org.iocaste.documents.common.ExtendedObject;

public class PageContext {
    public Map<String, TableToolContextEntry> tabletools;
    public Map<String, ExtendedObject> dataforms;
    
    public PageContext() {
        tabletools = new HashMap<>();
        dataforms = new HashMap<>();
    }
}