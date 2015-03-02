package org.iocaste.packagetool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.iocaste.appbuilder.common.ExtendedContext;
import org.iocaste.documents.common.ExtendedObject;

public class Context implements ExtendedContext {
    public Map<String, String> pkgsdata;
    public List<ExtendedObject> installed, uninstalled, invalid;
    
    public Context() {
        installed = new ArrayList<>();
        uninstalled = new ArrayList<>();
        invalid = new ArrayList<>();
    }
}
