package org.iocaste.kernel.runtime.shell.renderer;

import java.util.List;

import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Element;

public interface Renderer<T extends Element> {

    public abstract void run(
            List<XMLElement> parent, Container container, Config config);
    
    public abstract XMLElement run(Element element, Config config);
}
