package org.iocaste.runtime.common.portal.tiles;

import org.iocaste.runtime.common.page.AbstractViewInput;
import org.iocaste.runtime.common.portal.PortalContext;

public class PortalTileItemInput extends AbstractViewInput<PortalContext> {
    
    @Override
    public final void execute(PortalContext context) {
        textset("item", "");
    }
    
    @Override
    public final void init(PortalContext context) {
        execute(context);
    }
}