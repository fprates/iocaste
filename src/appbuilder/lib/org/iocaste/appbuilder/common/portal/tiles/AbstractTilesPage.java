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
        
        super.set((spec != null)? spec : new PortalTilesSpec());
        super.set((config != null)? config : new PortalTilesConfig());
        super.set((input != null)? input : new StandardViewInput());
        
        extcontext = getExtendedContext();
        extcontext.pagetiles = new PortalPageTiles();
        entry();
    }

    protected abstract void entry() throws Exception;
    
    @Override
    protected final void set(ViewConfig config) {
        ((PortalContext)getExtendedContext()).pagetiles.config = config;
    }
    
    @Override
    protected final void set(ViewInput input) {
        ((PortalContext)getExtendedContext()).pagetiles.input = input;
    }
    
    @Override
    protected final void set(ViewSpec spec) {
        ((PortalContext)getExtendedContext()).pagetiles.spec = spec;
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
