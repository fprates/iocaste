package org.iocaste.shell.common;

/**
 * Componente de parágrafo html.
 * 
 * Elemento padrão é <p>, mas pode ser alterado para outros.
 * 
 * @author francisco.prates
 *
 */
public class Text extends AbstractComponent {
    private static final long serialVersionUID = -6584462992412783994L;
    private String tag;
    
    public Text(View view, String name) {
        super(view, Const.TEXT, name);
        init();
    }
    
    public Text(Container container, String name) {
        super(container, Const.TEXT, name);
        init();
    }
    
    /**
     * Obtem o elemento html do componente. 
     * @return elemento html
     */
    public final String getTag() {
        return tag;
    }
    
    /**
     * 
     */
    private final void init() {
        tag = "p";
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
     * Ajusta o elemento html para o componente.
     * @param tag elemento html
     */
    public final void setTag(String tag) {
        this.tag = tag;
    }
}
