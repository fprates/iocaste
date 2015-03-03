package org.iocaste.packagetool;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.iocaste.appbuilder.common.ExtendedContext;
import org.iocaste.documents.common.ExtendedObject;

public class Context implements ExtendedContext {
    public Map<String, String> pkgsdata;
    public Set<ExtendedObject> installed, uninstalled, invalid;
    public Map<String, Throwable> exceptions;
    public Throwable exception;
    
    public Context() {
        installed = new LinkedHashSet<>();
        uninstalled = new LinkedHashSet<>();
        invalid = new LinkedHashSet<>();
        exceptions = new HashMap<>();
    }
}
