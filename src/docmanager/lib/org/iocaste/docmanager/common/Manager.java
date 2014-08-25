package org.iocaste.docmanager.common;

import java.util.Collection;
import java.util.Map;

import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.ExtendedObject;

public interface Manager {
    public static final int EEXISTS = 0;
    public static final int EINVALID = 1;
    public static final int SAVED = 2;
    
    public abstract boolean exists(Object code);
    
    public abstract ComplexDocument get(Object code);
    
    public abstract String getMessage(int messageid);
    
    public abstract ComplexModel getModel();

    public abstract Map<Object, ExtendedObject> getRelated(
            ComplexDocument document, String name, String field);
    
    public abstract ComplexDocument instance();
    
    public abstract void save(ExtendedObject head,
            Collection<ExtendedObject[]> items);
    
    public abstract void save(ComplexDocument document);
}
