package org.iocaste.appbuilder.common;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.cmodelviewer.DataFormContextEntry;
import org.iocaste.appbuilder.common.cmodelviewer.TableToolContextEntry;

public class PageContext {
    public Map<String, TableToolContextEntry> tabletools;
    public Map<String, DataFormContextEntry> dataforms;
    
    public PageContext() {
        tabletools = new HashMap<>();
        dataforms = new HashMap<>();
    }
}