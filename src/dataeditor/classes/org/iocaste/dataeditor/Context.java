package org.iocaste.dataeditor;

import java.util.HashSet;
import java.util.Set;

import org.iocaste.appbuilder.common.ExtendedContext;
import org.iocaste.documents.common.ExtendedObject;

public class Context implements ExtendedContext {
    public ExtendedObject[] items;
    public String action, model;
    public Set<ExtendedObject> deleted;
    
    public Context() {
        deleted = new HashSet<>();
    }
}
