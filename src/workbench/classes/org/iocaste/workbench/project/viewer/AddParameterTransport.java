package org.iocaste.workbench.project.viewer;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ExtendedObject;

public class AddParameterTransport extends AbstractParameterTransport {
    
    public AddParameterTransport(String action, String form) {
        super(action, form);
    }

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        ExtendedObject object = getdf(tool);
        ViewerUpdate updateviewer = execute(context, object);
        
        if (updateviewer != null)
            updateviewer.add(object);
    }
}