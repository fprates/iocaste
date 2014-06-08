package org.iocaste.workbench;

import org.iocaste.appbuilder.common.AbstractViewInput;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.NodeList;

public class ProjectInput extends AbstractViewInput {

    @Override
    protected void execute(PageBuilderContext context) {
        NodeList viewtree;
        
        Context extcontext = getExtendedContext();
        
//        viewtree = getElement("viewtree");
//        for (String view : extcontext.views.keySet())
//            new Text(viewtree, view);
    }

}
