package org.iocaste.infosis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.iocaste.appbuilder.common.ExtendedContext;
import org.iocaste.documents.common.ExtendedObject;

public class Context implements ExtendedContext {
    public List<Map<String, Object>> users;
    public List<ExtendedObject> report;
    
    public Context() {
        report = new ArrayList<>();
    }
}
