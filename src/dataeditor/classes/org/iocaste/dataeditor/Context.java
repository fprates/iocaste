package org.iocaste.dataeditor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.iocaste.appbuilder.common.ExtendedContext;
import org.iocaste.appbuilder.common.FieldProperty;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;

public class Context implements ExtendedContext {
    public Map<String, FieldProperty> properties;
    public List<ExtendedObject> items, originals;
    public String action, model, number, appname;
    public DocumentModelItem nsitem;
    public Object ns;
    public Documents documents;
    public ExtendedObject object;
    public boolean auto;
    
    public Context() {
        items = new ArrayList<>();
        originals = new ArrayList<>();
    }
}
