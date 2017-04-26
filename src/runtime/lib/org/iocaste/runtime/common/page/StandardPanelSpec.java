package org.iocaste.runtime.common.page;

import org.iocaste.runtime.common.application.Context;

public class StandardPanelSpec extends AbstractViewSpec {

    @Override
    protected final void execute(Context context) {
        ViewSpec extspec;
        AbstractPage page;
        
        form("main");
        navcontrol("main");
        standardcontainer("main", "navcontrol_cntnr");
        standardcontainer("main", "outercontent");
        standardcontainer("outercontent", "actionbar");
        standardcontainer("outercontent", "content");
        
        page = context.getPage();
        for (String key : page.getChildren()) {
            extspec = page.getChild(key).getSpec();
            if (extspec == null)
            	continue;
            spec(key.equals("navcontrol")?
            		"navcontrol_cntnr" : "content", extspec);
        }
    }
}
