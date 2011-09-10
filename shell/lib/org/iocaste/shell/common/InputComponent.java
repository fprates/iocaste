package org.iocaste.shell.common;

import org.iocaste.documents.common.DocumentModelItem;

public interface InputComponent {

    public abstract int getLength();
    
    public abstract DocumentModelItem getModelItem();

    public abstract String getValue();
    
    public abstract boolean isObligatory();
    
    public abstract void setLength(int length);
    
    public abstract void setModelItem(DocumentModelItem modelitem);
    
    public abstract void setObligatory(boolean obligatory);
    
    public abstract void setValue(String value);
}
