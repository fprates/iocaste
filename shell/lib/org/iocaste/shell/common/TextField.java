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
    
    public TextField(Container container, String name) {
        super(container, Const.TEXT_FIELD, null, name);
        setLength(20);
        setStyleClass("text_field");
    }
}
