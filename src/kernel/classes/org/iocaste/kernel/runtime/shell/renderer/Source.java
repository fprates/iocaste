package org.iocaste.kernel.runtime.shell.renderer;

public interface Source {
    public abstract Object run();
    public abstract void set(String name, Object object);
}

