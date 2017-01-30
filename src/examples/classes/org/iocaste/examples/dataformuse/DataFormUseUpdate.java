package org.iocaste.examples.dataformuse;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.examples.Context;

public class DataFormUseUpdate extends AbstractActionHandler {

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        Context extcontext = getExtendedContext();
        
        extcontext.dfuse.object = getdf("input");
    }

}
