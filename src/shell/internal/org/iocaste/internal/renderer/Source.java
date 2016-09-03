package org.iocaste.internal.renderer;

import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Element;

public interface Source {
    public abstract Object run();
    public abstract void set(String name, XMLElement element);
    public abstract void set(String name, Element element);
}

