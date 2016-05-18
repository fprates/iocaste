package org.iocaste.dataeditor;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class SelectionSpec extends AbstractViewSpec {

    @Override
    protected void execute(PageBuilderContext context) {
        dataform("content", "model");
    }
}
