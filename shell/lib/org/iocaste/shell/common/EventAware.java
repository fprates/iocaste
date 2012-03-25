package org.iocaste.shell.common;

import java.io.Serializable;

public interface EventAware extends Serializable {
    public static final byte ON_CLICK = 0;
    
    public abstract void onEvent(byte event, String args);
}
