package org.iocaste.kernel.runtime.shell.renderer.internal;

import java.util.List;

import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Element;

public interface Renderer<T extends Element> {
    
    public abstract Const getType();

    public abstract void run(List<XMLElement> parent,
            Container container, Config config) throws Exception;
    
    public abstract XMLElement run(Element element, Config config)
            throws Exception;
}
