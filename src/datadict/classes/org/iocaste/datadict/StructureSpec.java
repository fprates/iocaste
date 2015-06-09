package org.iocaste.datadict;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class StructureSpec extends AbstractViewSpec {

    @Override
    public final void execute(PageBuilderContext context) {
        form("main");
        navcontrol("main");
        dataform("main", "head");
        tabletool("main", "items");
    }
}
