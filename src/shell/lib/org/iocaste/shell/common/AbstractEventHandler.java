package org.iocaste.shell.common;

public abstract class AbstractEventHandler implements EventHandler {
    private static final long serialVersionUID = -1224820444752670125L;
    private byte inputerror;
    private Const msgtype;
    private View view;
    
    /**
     * 
     * @return
     */
    protected final byte getInputError() {
        return inputerror;
    }
    
    protected final Const getErrorType() {
        return msgtype;
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
     * @see org.iocaste.shell.common.EventHandler#setErrorType(
     *    org.iocaste.shell.common.Const)
     */
    @Override
    public final void setErrorType(Const msgtype) {
        this.msgtype = msgtype;
    }
    
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
