package org.iocaste.tasksel.tasks;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class TasksTileSpec extends AbstractViewSpec {

    @Override
    protected void execute(PageBuilderContext context) {
        link(parent, "tileitem");
        standardcontainer("tileitem", "frame");
        text("frame", "text");
    }
    
}
