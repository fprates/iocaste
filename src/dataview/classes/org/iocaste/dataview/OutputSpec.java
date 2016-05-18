package org.iocaste.dataview;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class OutputSpec extends AbstractViewSpec {

    @Override
    protected void execute(PageBuilderContext context) {
        reporttool("content", "items");
    }

}
