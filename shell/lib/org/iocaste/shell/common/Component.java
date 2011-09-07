package org.iocaste.shell.common;

import org.iocaste.documents.common.DocumentModelItem;

public interface Component extends Element {

    public abstract Container getContainer();
    
    public abstract DocumentModelItem getModelItem();
    
    public abstract void setModelItem(DocumentModelItem modelitem);
}
