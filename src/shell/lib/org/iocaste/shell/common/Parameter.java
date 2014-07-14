package org.iocaste.shell.common;

/**
 * Implementação de componente de parâmetro html.
 * 
 * @author francisco.prates
 *
 */
public class Parameter extends AbstractInputComponent {
    private static final long serialVersionUID = -8750478677094210518L;
    
    public Parameter(View view, String name) {
        super(view, Const.PARAMETER, null, name);
    }
    
    public Parameter(Container container, String name) {
        super(container, Const.PARAMETER, null, name);
    }
}
