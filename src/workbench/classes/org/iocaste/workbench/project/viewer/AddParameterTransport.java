package org.iocaste.workbench.project.viewer;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Const;

public class AddParameterTransport extends AbstractParameterTransport {
    
    public AddParameterTransport(String action, String form) {
        super(action, form);
    }

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        ExtendedObject object = getdf(tool);
        ViewerUpdate updateviewer = execute(context, object);
        
        if (context.function.getMessageType() == Const.ERROR)
            return;
        
        if (updateviewer != null)
            updateviewer.add(object);
    }
}