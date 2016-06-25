package org.iocaste.documents.common;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class ComplexDocumentItems implements Serializable {
    private static final long serialVersionUID = -5614475381515365460L;
    public Map<Object, ExtendedObject> objects;
    public Map<Object, ComplexDocument> documents;
    
    public ComplexDocumentItems(ComplexModelItem modelitem) {
        if (modelitem.model != null)
            objects = new LinkedHashMap<>();
        else
            documents = new LinkedHashMap<>();
    }
    
    public final void clear() {
        if (objects != null)
            objects.clear();
        else
            documents.clear();
    }
}