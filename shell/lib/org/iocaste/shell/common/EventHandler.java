package org.iocaste.shell.common;

import java.io.Serializable;

/**
 * Manipulador de evento para elementos.
 * 
 * @author francisco.prates
 *
 */
public interface EventHandler extends Serializable {
    public static final byte ON_CLICK = 0;
    
    public abstract void onEvent(byte event, String args);
}
