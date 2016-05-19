package org.iocaste.datadict;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class StructureSpec extends AbstractViewSpec {

    @Override
    public final void execute(PageBuilderContext context) {
        dataform(parent, "head");
        tabletool(parent, "items");
    }
}
