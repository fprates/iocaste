package org.iocaste.tasksel;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class ItemTileSpec extends AbstractViewSpec {

    @Override
    protected void execute(PageBuilderContext context) {
        link(parent, "tileitem");
        standardcontainer("tileitem", "frame");
        text("frame", "name");
    }
    
}
