package org.iocaste.shell.common;

/**
 * Checkbox html.
 * 
 * Define alguns métodos hardcode apenas para orientação do controlador.
 * 
 * @author francisco.prates
 *
 */
public class CheckBox extends AbstractInputComponent {
    private static final long serialVersionUID = -7042599709545616205L;
    
    public CheckBox(View view, String name) {
        super(view, Const.CHECKBOX, null, name);
        setStyleClass("checkbox");
    }
    
    public CheckBox(Container container, String name) {
        super(container, Const.CHECKBOX, null, name);
        setStyleClass("checkbox");
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
