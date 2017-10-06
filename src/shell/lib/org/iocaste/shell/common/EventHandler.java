package org.iocaste.shell.common;

import java.io.Serializable;

/**
 * Manipulador de evento para elementos.
 * 
 * @author francisco.prates
 *
 */
public interface EventHandler extends Serializable {
    
    /**
     * 
     * @param control
     */
    public abstract void onEvent(ControlComponent control);
    
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
