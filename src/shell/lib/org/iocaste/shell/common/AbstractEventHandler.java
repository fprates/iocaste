package org.iocaste.shell.common;

public abstract class AbstractEventHandler implements EventHandler {
    private static final long serialVersionUID = -1224820444752670125L;
    private byte inputerror;
    private View view;
    
    /**
     * 
     * @return
     */
    protected final byte getInputError() {
        return inputerror;
    }
    
    /**
     * 
     * @return
     */
    protected final View getView() {
        return view;
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
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.shell.common.EventHandler#setView(
     *    org.iocaste.shell.common.View)
     */
    @Override
    public final void setView(View view) {
        this.view = view;
    }

}
