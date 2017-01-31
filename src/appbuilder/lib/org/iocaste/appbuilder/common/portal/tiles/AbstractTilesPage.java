package org.iocaste.appbuilder.common.portal.tiles;

import org.iocaste.appbuilder.common.StandardViewInput;
import org.iocaste.appbuilder.common.ViewConfig;
import org.iocaste.appbuilder.common.ViewInput;
import org.iocaste.appbuilder.common.ViewSpec;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.appbuilder.common.portal.PortalContext;

public abstract class AbstractTilesPage extends AbstractPanelPage {
    private ViewSpec spec;
    private ViewConfig config;
    private ViewInput input;
    
    @Override
    public final void execute() throws Exception {
        PortalContext extcontext;
        PortalPageTiles tilesdata;
        
        super.set((spec != null)? spec : new PortalTilesSpec());
        super.set((config != null)? config : new PortalTilesConfig());
        super.set((input != null)? input : new StandardViewInput());
        
        extcontext = getExtendedContext();
        extcontext.pagetiles.put(getName(), tilesdata = new PortalPageTiles());
        tilesdata.setTitle(getName());
        entry();
    }

    protected abstract void entry() throws Exception;
    
    private final PortalPageTiles getPageTiles() {
        PortalContext extcontext = getExtendedContext();
        return extcontext.pagetiles.get(getName());
    }
    
    @Override
    protected final void set(ViewConfig config) {
        getPageTiles().config = config;
    }
    
    @Override
    protected final void set(ViewInput input) {
        getPageTiles().input = input;
    }
    
    @Override
    protected final void set(ViewSpec spec) {
        getPageTiles().spec = spec;
    }
    
    protected final void setParent(ViewConfig config) {
        this.config = config;
    }
    
    protected final void setParent(ViewInput input) {
        this.input = input;
    }
    
    protected final void setParent(ViewSpec spec) {
        this.spec = spec;
    }

}
