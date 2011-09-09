package org.iocaste.shell.common;

import org.iocaste.documents.common.DocumentModelItem;

public interface InputComponent {

    public abstract String getValue();
    
    public abstract void setValue(String value);

    public abstract DocumentModelItem getModelItem();
    
    public abstract boolean isObligatory();
    
    public abstract void setModelItem(DocumentModelItem modelitem);
    
    public abstract void setObligatory(boolean obligatory);
}
