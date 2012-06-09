package org.iocaste.shell.common;

public class ExpandBar extends AbstractContainer {
    private static final long serialVersionUID = -4962118925903091404L;
    private boolean expanded;
    
    /**
     * 
     * @param container
     * @param name
     */
    public ExpandBar(Container container, String name) {
        super(container, Const.EXPAND_BAR, name);
        
        init();
    }

    /**
     * 
     * @param view
     * @param name
     */
    public ExpandBar(View view, String name) {
        super(view, Const.EXPAND_BAR, name);
        
        init();
    }
    
    /**
     * 
     * @return
     */
    public final EventAware getHandler() {
        return new ExpandBarHandler(this);
    }
    
    /**
     * 
     */
    private final void init() {
        expanded = true;
        setStyleClass("expand_bar");
    }
    
    /**
     * 
     * @return
     */
    public final boolean isExpanded() {
        return expanded;
    }

    /**
     * 
     * @param expanded
     */
    public final void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}

class ExpandBarHandler implements EventAware {
    private static final long serialVersionUID = -4701284786729026501L;
    private ExpandBar ebar;
    
    public ExpandBarHandler(ExpandBar ebar) {
        this.ebar = ebar;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.EventAware#onEvent(byte, java.lang.String)
     */
    @Override
    public void onEvent(byte event, String action) {
        ebar.setExpanded(!ebar.isExpanded());
    }
}