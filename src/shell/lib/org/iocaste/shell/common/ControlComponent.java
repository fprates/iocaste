package org.iocaste.shell.common;

public interface ControlComponent {
    public static final boolean CANCELLABLE = true;
    public static final boolean NON_CANCELLABLE = false;
    
    public abstract boolean isCancellable();

    public abstract void setCancellable(boolean cancellable);
}
