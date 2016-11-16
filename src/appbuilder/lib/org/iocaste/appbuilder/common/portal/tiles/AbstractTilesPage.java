package org.iocaste.appbuilder.common.portal.tiles;

import org.iocaste.appbuilder.common.StandardViewInput;
import org.iocaste.appbuilder.common.ViewConfig;
import org.iocaste.appbuilder.common.ViewInput;
import org.iocaste.appbuilder.common.ViewSpec;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;

public abstract class AbstractTilesPage extends AbstractPanelPage {
    private PortalTilesConfig config;
    
    @Override
    public final void execute() throws Exception {
        super.set(new PortalTilesSpec());
        super.set(config = new PortalTilesConfig());
        super.set(new StandardViewInput());
        config.pagetiles = new PortalPageTiles();
        entry();
    }

    protected abstract void entry();
    
    @Override
    protected final void set(ViewConfig config) {
        this.config.pagetiles.config = config;
    }
    
    @Override
    protected final void set(ViewInput input) {
        config.pagetiles.input = input;
    }
    
    @Override
    protected final void set(ViewSpec spec) {
        config.pagetiles.spec = spec;
    }

}
