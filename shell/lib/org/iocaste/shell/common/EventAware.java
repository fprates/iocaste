package org.iocaste.shell.common;

public interface EventAware {
    public static final byte ON_CLICK = 0;
    
    public abstract void onEvent(byte event, String args);
}
