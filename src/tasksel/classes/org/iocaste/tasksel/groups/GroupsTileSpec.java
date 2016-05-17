package org.iocaste.tasksel.groups;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class GroupsTileSpec extends AbstractViewSpec {

    @Override
    protected void execute(PageBuilderContext context) {
        link(parent, "tileitem");
        standardcontainer("tileitem", "frame");
        text("frame", "name");
    }
    
}
