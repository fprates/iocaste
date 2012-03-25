package org.iocaste.shell.common;

public interface ControlComponent extends Element {

    public abstract boolean allowStacking();
    
    public abstract String getAction();
    
    public abstract boolean isCancellable();
    
    public abstract void onEvent(byte event, String args);
    
    public abstract void setAction(String action);
    
    public abstract void setAllowStacking(boolean stacking);
    
    public abstract void setCancellable(boolean cancellable);
    
    public abstract void setEventHandler(EventAware eventhandler);
}
