package org.iocaste.shell.common;

public interface ControlComponent {
    
    public abstract String getAction();
    
    public abstract boolean isCancellable();

    public abstract boolean isHelpControl();
    
    public abstract void setAction(String action);
    
    public abstract void setCancellable(boolean cancellable);
    
    public abstract void setHelpControl(boolean helpcontrol);
}
