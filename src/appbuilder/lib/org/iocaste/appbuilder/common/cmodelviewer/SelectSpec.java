package org.iocaste.appbuilder.common.cmodelviewer;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.panel.AbstractPanelSpec;

public class SelectSpec extends AbstractPanelSpec {

    @Override
    public void execute(PageBuilderContext context) {
        Context extcontext = getExtendedContext();
        
        extcontext.pageInstance();
        dataform("content", "head");
    }

}