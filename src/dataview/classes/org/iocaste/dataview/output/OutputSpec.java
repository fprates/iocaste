package org.iocaste.dataview.output;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class OutputSpec extends AbstractViewSpec {

    @Override
    protected void execute(PageBuilderContext context) {
        reporttool(parent, "items");
    }

}
