package org.iocaste.shell.common;

public interface PopupControl extends ControlComponent, Component {
    
    public abstract boolean isReady(PopupControl popupcontrol);
    
    public abstract void update(String action, Object value);

}
