package org.iocaste.dataeditor;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.appbuilder.common.ExtendedContext;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;

public class Context implements ExtendedContext {
    public List<ExtendedObject> items;
    public String action, model, number;
    public DocumentModelItem nsitem;
    public Object ns;
    public Documents documents;
    public ExtendedObject object;
    
    public Context() {
        items = new ArrayList<>();
    }
}
