package org.iocaste.shell.common;

import org.iocaste.documents.common.DocumentModelItem;

public interface InputComponent {

    public abstract String getValue();
    
    public abstract void setValue(String value);

    public abstract DocumentModelItem getModelItem();
    
    public abstract void setModelItem(DocumentModelItem modelitem);
}
