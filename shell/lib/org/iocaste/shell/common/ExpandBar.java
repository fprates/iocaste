package org.iocaste.shell.common;

/**
 * Define área que pode ser expandida ou contraída se barra do
 * container for clicada.
 * 
 * @author francisco.prates
 *
 */
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
     */
    private final void init() {
        expanded = true;
        setStyleClass("expand_bar");
        setEventHandler(new ExpandBarHandler(this));
    }
    
    /**
     * Indica se área está expandida.
     * @return true, se área estiver expandida.
     */
    public final boolean isExpanded() {
        return expanded;
    }

    /**
     * Ajusta estado da expansão da área.
     * @param expanded true, se área está expandida.
     */
    public final void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}

class ExpandBarHandler implements EventHandler {
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