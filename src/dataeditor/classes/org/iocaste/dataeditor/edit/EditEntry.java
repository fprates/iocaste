package org.iocaste.dataeditor.edit;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class EditEntry extends AbstractActionHandler {

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        init("select", getExtendedContext());
        redirect("select");
    }

}
