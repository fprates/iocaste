package org.iocaste.shell.common;

/**
 * Componente de entrada TextArea.
 * 
 * O CSS desse componente ajusta a caixa para a larga total da página.
 * No momento, customize o CSS para personalizar esse componente.
 * 
 * @author francisco.prates
 *
 */
public class TextArea extends AbstractInputComponent {
    private static final long serialVersionUID = 4848464288942587299L;

    public TextArea(Container container, String name) {
        super(container, Const.TEXT_AREA, null, name);
        
        setStyleClass("textarea");
    }
}
