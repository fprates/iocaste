package org.iocaste.documents.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ComplexDocumentItems implements Serializable {
    private static final long serialVersionUID = -5614475381515365460L;
    public List<ExtendedObject> objects;
    public List<ComplexDocument> documents;
    
    public ComplexDocumentItems(ComplexModelItem modelitem) {
        if (modelitem.model != null)
            objects = new ArrayList<>();
        else
            documents = new ArrayList<>();
    }
    
    public final void clear() {
        if (objects != null)
            objects.clear();
        else
            documents.clear();
    }
}