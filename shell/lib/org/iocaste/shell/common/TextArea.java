package org.iocaste.shell.common;

public class TextArea extends AbstractInputComponent {
    private static final long serialVersionUID = 4848464288942587299L;

    public TextArea(Container container, String name) {
        super(container, Const.TEXT_AREA, null, name);
        
        setStyleClass("textarea");
    }
}
