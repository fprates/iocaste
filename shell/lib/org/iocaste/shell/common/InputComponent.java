package org.iocaste.shell.common;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DocumentModelItem;

public interface InputComponent extends Component {

    public abstract Const getComponentType();
    
    public abstract DataElement getDataElement();
    
    public abstract int getLength();
    
    public abstract DocumentModelItem getModelItem();

    public abstract SearchHelp getSearchHelp();
    
    public abstract String getValue();
    
    public abstract boolean isBooleanComponent();
    
    public abstract boolean isObligatory();
    
    public abstract boolean isReferenceValidable();
    
    public abstract boolean isSecret();
    
    public abstract boolean isSelectable();
    
    public abstract boolean isSelected();
    
    public abstract Object getParsedValue();
    
    public abstract void setComponentType(Const type);
    
    public abstract void setDataElement(DataElement dataelement);
    
    public abstract void setLength(int length);
    
    public abstract void setModelItem(DocumentModelItem modelitem);
    
    public abstract void setObligatory(boolean obligatory);
    
    public abstract void setReferenceValidable(boolean refvalidable);
    
    public abstract void setSearchHelp(SearchHelp search);
    
    public abstract void setSecret(boolean secret);
    
    public abstract void setSelected(boolean selected);
    
    public abstract void setValue(String value);
}
