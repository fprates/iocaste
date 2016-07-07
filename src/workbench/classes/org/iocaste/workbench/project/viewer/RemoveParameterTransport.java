package org.iocaste.workbench.project.viewer;

import java.util.List;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ExtendedObject;

public class RemoveParameterTransport extends AbstractParameterTransport {
    
    public RemoveParameterTransport(String action, String table) {
        super(action, table);
    }

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        List<ExtendedObject> objects = tableselectedget(tool);
        ViewerUpdate updateviewer;
        
        if (objects == null)
            return;
        for (ExtendedObject object : objects) {
            updateviewer = execute(context, object);
            if (updateviewer != null)
                updateviewer.remove(object);
            break;
        }
    }
}