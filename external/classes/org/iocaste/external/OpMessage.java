package org.iocaste.external;

import java.util.ArrayList;
import java.util.List;

public class OpMessage {
    public String name;
    public List<Type> parameters;
    
    public OpMessage() {
        parameters = new ArrayList<>();
    }
    
    @Override
    public String toString() {
        return name;
    }
}
