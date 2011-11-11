package org.iocaste.shell.common;

public class Text extends AbstractComponent {
    private static final long serialVersionUID = -6584462992412783994L;
    private String text;
    
    public Text(Container container, String name) {
        super(container, Const.TEXT, name);
    }
    
    public final String getText() {
        return text;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#isControlComponent()
     */
    @Override
    public final boolean isControlComponent() {
        return false;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#isDataStorable()
     */
    @Override
    public final boolean isDataStorable() {
        return false;
    }
    
    public final void setText(String text) {
        this.text = text;
    }

}
