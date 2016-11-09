package org.iocaste.upload.ns;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class NSInputSpec extends AbstractViewSpec {

    @Override
    protected void execute(PageBuilderContext context) {
        dataform(parent, "ns");
    }

}
