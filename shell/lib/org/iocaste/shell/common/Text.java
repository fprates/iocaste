package org.iocaste.shell.common;

public class Text extends AbstractComponent {
    private static final long serialVersionUID = -6584462992412783994L;
    private String tag;
    
    public Text(Container container, String name) {
        super(container, Const.TEXT, name);
        
        tag = "p";
    }
    
    /**
     * 
     * @return
     */
    public final String getTag() {
        return tag;
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
    
    /**
     * 
     * @param tag
     */
    public final void setTag(String tag) {
        this.tag = tag;
    }
}
