package org.iocaste.kernel.runtime.shell.renderer.internal;

public interface Source {
    public abstract Object run();
    public abstract void set(String name, Object object);
}

