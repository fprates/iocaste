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
    private String text, edgestyle, internalstyle;
    
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
     * Retorna o estilo da barra
     * @return nome do estilo
     */
    public final String getEdgeStyle() {
        return edgestyle;
    }
    
    /**
     * Retorna o estilo da área internal
     * @return nome do estilo
     */
    public final String getInternalStyle() {
        return internalstyle;
    }
    
    /**
     * Retorna texto da barra
     * @return texto
     */
    public final String getText() {
        return text;
    }
    
    /**
     * 
     */
    private final void init() {
        expanded = true;
        internalstyle = "eb_internal";
        edgestyle = "eb_edge";
        setStyleClass("eb_external");
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
     * Define estilo da barra
     * @param edgestyle nome do estilo
     */
    public final void setEdgeStyle(String edgestyle) {
        this.edgestyle = edgestyle;
    }
    
    /**
     * Ajusta estado da expansão da área.
     * @param expanded true, se área está expandida.
     */
    public final void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
    
    /**
     * Define estilo da área interna
     * @param internalstyle nome do estilo
     */
    public final void setInternalStyle(String internalstyle) {
        this.internalstyle = internalstyle;
    }
    
    /**
     * Define texto da barra
     * @param text texto
     */
    public final void setText(String text) {
        this.text = text;
    }
}

class ExpandBarHandler extends AbstractEventHandler {
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
    public final void onEvent(byte event, String action) {
        ebar.setExpanded(!ebar.isExpanded());
    }
}