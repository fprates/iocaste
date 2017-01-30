package org.iocaste.examples;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.portal.PortalContext;
import org.iocaste.examples.dataformuse.DataFormUseContext;

public class Context extends PortalContext {
    public DataFormUseContext dfuse;
    
    public Context(PageBuilderContext context) {
        super(context);
        dfuse = new DataFormUseContext();
    }
    
}