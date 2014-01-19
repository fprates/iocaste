package org.iocaste.external;

import java.util.Set;

public class Operation {
    private OperationData opdata;
    
    public Operation(OperationData opdata) {
        this.opdata = opdata;
    }
    
    public final Type getInput(String parameter) {
        return opdata.input.get(parameter);
    }
    
    public final Set<String> getInputKeys() {
        return opdata.input.keySet();
    }
    
    public final Type getOutput(String parameter) {
        return opdata.output.get(parameter);
    }
    
    public final Set<String> getOutputKeys() {
        return opdata.output.keySet();
    }
    
    @Override
    public String toString() {
        return opdata.name;
    }
}
