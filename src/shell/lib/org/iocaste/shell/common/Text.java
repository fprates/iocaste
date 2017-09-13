package org.iocaste.shell.common;

/**
 * Componente de parágrafo html.
 * 
 * Elemento padrão é <p>, mas pode ser alterado para outros.
 * 
 * @author francisco.prates
 *
 */
public class Text extends AbstractComponent implements TextComponent {
    private static final long serialVersionUID = -6584462992412783994L;
    private String tag, mask;
    
    public Text(View view, String name) {
        super(view, Const.TEXT, name);
        init();
    }
    
    public Text(Container container, String name) {
        super(container, Const.TEXT, name);
        init();
    }
    
    @Override
    public final String getMask() {
        return mask;
    }
    
    @Override
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
    
    @Override
    public final void setMask(String mask) {
        this.mask = mask;
    }
    
    @Override
    public final void setTag(String tag) {
        this.tag = tag;
    }
}
