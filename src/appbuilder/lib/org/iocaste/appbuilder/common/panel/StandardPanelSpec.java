package org.iocaste.appbuilder.common.panel;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.ViewSpec;

public class StandardPanelSpec extends AbstractViewSpec {
    private AbstractPanelPage page;
    
    public StandardPanelSpec(AbstractPanelPage page) {
        this.page = page;
    }

    @Override
    protected final void execute(PageBuilderContext context) {
        ViewSpec extspec;
        
        form("main");
        navcontrol("main");

        standardcontainer("main", "outercontent");
        standardcontainer("outercontent", "content");
        
        extspec = page.getSpec();
        if (extspec != null)
            spec("content", extspec);
    }
}
