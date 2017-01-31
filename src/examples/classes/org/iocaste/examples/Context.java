package org.iocaste.examples;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.portal.PortalContext;
import org.iocaste.examples.dataformuse.DataFormUseContext;
import org.iocaste.examples.tabletooluse.TableToolUseContext;

public class Context extends PortalContext {
    public DataFormUseContext dfuse;
    public TableToolUseContext ttuse;
    
    public Context(PageBuilderContext context) {
        super(context);
        dfuse = new DataFormUseContext();
        ttuse = new TableToolUseContext();
    }
    
}