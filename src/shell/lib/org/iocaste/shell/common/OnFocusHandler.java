package org.iocaste.shell.common;

public class OnFocusHandler extends AbstractEventHandler {
    private static final long serialVersionUID = -6628615220035348184L;
    private Element element;
    
    public OnFocusHandler(Element element) {
        this.element = element;
    }
    
    @Override
    public final void onEvent(ControlComponent control) {
        element.getView().setFocus(element);
    }
}
