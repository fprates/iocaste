package org.iocaste.external;

import java.util.Set;

public class Type {
    public static final byte INPUT = 0;
    public static final byte OUTPUT = 1;
    private TypeData typedata;
    
    public Type(TypeData typedata) {
        this.typedata = typedata;
    }
    
    public final String getAbsoluteName() {
        return typedata.aname;
    }
    
    public final Type getField(String name) {
        return typedata.fields.get(name);
    }
    
    public final Set<String> getFieldsKeys() {
        return typedata.fields.keySet();
    }
    
    public final String getName() {
        return typedata.name;
    }
    
    public final String getValueType() {
        return typedata.ctype;
    }
    
    @Override
    public String toString() {
        return typedata.name;
    }
}
