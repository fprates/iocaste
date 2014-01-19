package org.iocaste.shell.common;

public abstract class AbstractEventHandler implements EventHandler {
    private static final long serialVersionUID = -1224820444752670125L;
    private byte inputerror;
    
    /**
     * 
     * @return
     */
    protected final byte getInputError() {
        return inputerror;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.EventHandler#
     *     onEvent(byte, java.lang.String)
     */
    @Override
    public abstract void onEvent(byte event, String args);

    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.EventHandler#setInputError(byte)
     */
    @Override
    public final void setInputError(byte error) {
        inputerror = error;
    }

}
