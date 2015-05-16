package org.iocaste.appbuilder.common.panel;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.appbuilder.common.AbstractViewSpec;

public abstract class AbstractPanelSpec extends AbstractViewSpec {
    private List<PanelPageItem> ctxitems;
    
    public AbstractPanelSpec() {
        ctxitems = new ArrayList<>();
    }
    
    protected final void contextitem(PanelPageItem item) {
        ctxitems.add(item);
    }
    
    public final List<PanelPageItem> getContextItems() {
        return ctxitems;
    }
}
