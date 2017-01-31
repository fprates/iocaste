package org.iocaste.appbuilder.common.portal.tiles;

import org.iocaste.appbuilder.common.ViewConfig;
import org.iocaste.appbuilder.common.ViewInput;
import org.iocaste.appbuilder.common.ViewSpec;

public class PortalPageTiles {
    public ViewSpec spec;
    public ViewConfig config;
    public ViewInput input;
    public String title;
    public Object[] titleargs;
    
    public final void setTitle(String title, Object... args) {
        this.title = title;
        this.titleargs = args;
    }
}
