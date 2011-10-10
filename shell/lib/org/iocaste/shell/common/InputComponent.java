package org.iocaste.shell.common;

import org.iocaste.documents.common.DocumentModelItem;

public interface InputComponent {

    public abstract Const getComponentType();
    
    public abstract int getLength();
    
    public abstract DocumentModelItem getModelItem();

    public abstract String getValue();
    
    public abstract boolean isObligatory();
    
    public abstract boolean isSecret();
    
    public abstract void setLength(int length);
    
    public abstract void setModelItem(DocumentModelItem modelitem);
    
    public abstract void setObligatory(boolean obligatory);
    
    public abstract void setSecret(boolean secret);
    
    public abstract void setValue(String value);
}
