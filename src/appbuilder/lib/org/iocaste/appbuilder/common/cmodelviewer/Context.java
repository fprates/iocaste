package org.iocaste.appbuilder.common.cmodelviewer;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.AppBuilderLink;
import org.iocaste.appbuilder.common.ExtendedContext;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.ExtendedObject;

public class Context implements ExtendedContext {
    public Object id, ns;
    public String redirect; 
    public ComplexDocument document;
    public AppBuilderLink link;
    public Map<String, Map<Integer, ExtendedObject>> tabletools;
    
    public Context() {
        tabletools = new HashMap<>();
    }
    
    public final void add(String ttname, ExtendedObject object) {
        Map<Integer, ExtendedObject> items = tabletools.get(ttname);
        items.put(items.size(), object);
    }
}
