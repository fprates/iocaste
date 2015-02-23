package org.iocaste.shell.common;

public abstract class PopupControl extends AbstractControlComponent {
    private static final long serialVersionUID = 2707877706423406327L;
    private String application;
    
    public PopupControl(Container container, Const type, String name) {
        super(container, type, name);
    }
    
    public final String getApplication() {
        return application;
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.shell.common.AbstractControlComponent#isPopup()
     */
    @Override
    public final boolean isPopup() {
        return true;
    }
    
    public final void setApplication(String application) {
        this.application = application;
    }
    
    public abstract void update(View view);

}
