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
    public static final byte ON_FOCUS = 1;
    
    /**
     * 
     * @param event
     * @param args
     */
    public abstract void onEvent(byte event, ControlComponent control);
    
    /**
     * 
     * @param msgtype
     */
    public abstract void setErrorType(Const msgtype);
    
    /**
     * 
     * @param error
     */
    public abstract void setInputError(int error);
    
    /**
     * 
     * @param view
     */
    public abstract void setView(View view);
}
