package org.iocaste.shell.common;

public interface ControlComponent {

    public abstract boolean isCancellable();

    public abstract void setCancellable(boolean cancellable);
}
