package org.iocaste.runtime.common.portal.tiles;

import org.iocaste.runtime.common.application.Context;
import org.iocaste.runtime.common.page.AbstractViewInput;

public class PortalTileItemInput extends AbstractViewInput<Context> {
    
    @Override
    public final void execute(Context context) {
        textset("item", "");
    }
    
    @Override
    public final void init(Context context) {
        execute(context);
    }
}