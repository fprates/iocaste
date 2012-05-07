package org.iocaste.shell.common;

public class CheckBox extends AbstractInputComponent {
    private static final long serialVersionUID = -7042599709545616205L;
    
    public CheckBox(Container container, String name) {
        super(container, Const.CHECKBOX, null, name);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.AbstractInputComponent#isBooleanComponent()
     */
    @Override
    public final boolean isBooleanComponent() {
        return true;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.AbstractInputComponent#isSelectable()
     */
    @Override
    public final boolean isSelectable() {
        return true;
    }
}
