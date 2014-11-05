package org.iocaste.protocol;

public class Tests {
    private long init, term;
    
    public void start() {
        init = System.currentTimeMillis();
    }
    
    public void finish() {
        term = System.currentTimeMillis();
    }
    
    public long timestamp() {
        return term - init;
    }
}
