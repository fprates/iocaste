package org.iocaste.shell.common;

public abstract class AbstractPopupControl extends AbstractControlComponent
        implements PopupControl {
    private static final long serialVersionUID = 2707877706423406327L;
    
    public AbstractPopupControl(Container container, Const type, String name) {
        super(container, type, name);
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.shell.common.AbstractControlComponent#isPopup()
     */
    @Override
    public final boolean isPopup() {
        return true;
    }

}
