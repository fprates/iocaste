package org.iocaste.shell.common;

/**
 * Botão html.
 * 
 * A ação chamada por padrão é o nome do botão.
 * submit é ajustável, false por default.
 * 
 * @author francisco.prates
 *
 */
public class Button extends AbstractControlComponent {
    private static final long serialVersionUID = 2295137293981769652L;
    private boolean submit;
    
    public Button(View view, String name) {
        super(view, Const.BUTTON, name);
        submit = false;
    }
    
    public Button(Container container, String name) {
        super(container, Const.BUTTON, name);
        submit = false;
    }
    
    /**
     * Retorna se é submit.
     * @return true, se for submit.
     */
    public final boolean isSubmit() {
        return submit;
    }
    
    /**
     * Ajusta propriedade submit.
     * @param submit true, para submit
     */
    public final void setSubmit(boolean submit) {
        this.submit = submit;
    }
}