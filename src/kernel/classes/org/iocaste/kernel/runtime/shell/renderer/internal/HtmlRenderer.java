package org.iocaste.kernel.runtime.shell.renderer.internal;

import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Element;

public interface HtmlRenderer {
    
    public abstract void add(Renderer<? extends Element> renderer);
    
    public abstract <T extends Renderer<? extends Element>> T getRenderer(
            Const type);
    
}
