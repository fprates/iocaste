package org.iocaste.workbench.project;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class ProjectsTileSpec extends AbstractViewSpec {

    @Override
    protected void execute(PageBuilderContext context) {
        link(parent, "item");
        standardcontainer("item", "frame");
        text("frame", "name");
    }
}
