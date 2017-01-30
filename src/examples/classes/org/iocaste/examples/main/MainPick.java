package org.iocaste.examples.main;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class MainPick extends AbstractActionHandler {

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        String id = getinputst("item_NAME");
        init(id, getExtendedContext());
        redirect(id);
    }
    
}