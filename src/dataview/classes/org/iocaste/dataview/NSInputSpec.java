package org.iocaste.dataview;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class NSInputSpec extends AbstractViewSpec {

    @Override
    protected void execute(PageBuilderContext context) {
        dataform("content", "ns");
    }

}
