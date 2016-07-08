package org.iocaste.workbench.project.view.config;

import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.workbench.Context;

public interface ViewElementAttribute {
    
    public abstract String getName();
    
    public abstract ExtendedObject instance(ComplexDocument spec, String value);
    
    public abstract void setContext(Context extcontext);
}