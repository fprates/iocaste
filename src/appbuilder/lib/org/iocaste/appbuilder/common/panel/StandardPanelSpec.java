package org.iocaste.appbuilder.common.panel;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.ViewSpec;

public class StandardPanelSpec extends AbstractViewSpec {

    @Override
    protected final void execute(PageBuilderContext context) {
        ViewSpec extspec;
        AbstractPanelPage ncdesign;
        
        form("main");
        navcontrol("main");
        standardcontainer("main", "navcontrol_cntnr");
        standardcontainer("main", "outercontent");
        standardcontainer("outercontent", "actionbar");
        standardcontainer("outercontent", "content");
        
        ncdesign = context.getView().getDesign();
        if (ncdesign != null)
            spec("navcontrol_cntnr", ncdesign.getSpec());
        
        extspec = context.getView().getPanelPage().getSpec();
        if (extspec != null)
            spec("content", extspec);
    }
}
