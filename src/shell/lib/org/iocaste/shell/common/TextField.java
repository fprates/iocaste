package org.iocaste.shell.common;

/**
 * Campo de entrada de texto.
 * 
 * Comprimento da entrada é de 20 caracteres, por padrão.
 * 
 * @author francisco.prates
 *
 */
public class TextField extends AbstractInputComponent {
    private static final long serialVersionUID = 4027561075976606307L;
    public static final String STYLE = "text_field";
    
    public TextField(View view, String name) {
        super(view, Const.TEXT_FIELD, null, name);
        init();
    }
    
    public TextField(Container container, String name) {
        super(container, Const.TEXT_FIELD, null, name);
        init();
    }
    
    private final void init() {
        if (getLength() == 0 && getModelItem() == null) {
            setLength(20);
            setVisibleLength(20);
        }
        setStyleClass(STYLE);
    }
}
