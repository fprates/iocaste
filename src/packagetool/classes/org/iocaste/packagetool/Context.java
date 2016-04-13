package org.iocaste.packagetool;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.iocaste.appbuilder.common.AbstractExtendedContext;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ExtendedObject;

public class Context extends AbstractExtendedContext {
    public Map<String, String> pkgsdata;
    public Set<ExtendedObject> installed, uninstalled, invalid;
    public Map<String, Throwable> exceptions;
    public Throwable exception;
    
    public Context(PageBuilderContext context) {
        super(context);
        installed = new LinkedHashSet<>();
        uninstalled = new LinkedHashSet<>();
        invalid = new LinkedHashSet<>();
        exceptions = new HashMap<>();
    }
}
