package org.iocaste.appbuilder.common.panel;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.ViewSpec;
import org.iocaste.appbuilder.common.ViewSpecItem;
import org.iocaste.appbuilder.common.ViewSpecItem.TYPES;

public class StandardPanelSpec extends AbstractViewSpec {
    private AbstractPanelPage page;
    
    public StandardPanelSpec(AbstractPanelPage page) {
        this.page = page;
    }

    @Override
    protected final void execute(PageBuilderContext context) {
        ViewSpec extspec;
        ViewSpecItem.TYPES type;
        String parent, name;
        
        form("main");
        navcontrol("main");
        for (int i = 0; i < context.ncspec.length; i++) {
            type = (TYPES)context.ncspec[i][0];
            parent = (String)context.ncspec[i][1];
            name = (String)context.ncspec[i][2];
            component(type, parent, name);
        }

        standardcontainer("main", "outercontent");
        standardcontainer("outercontent", "actionbar");
        standardcontainer("outercontent", "content");
        
        extspec = page.getSpec();
        if (extspec != null)
            spec("content", extspec);
    }
}
