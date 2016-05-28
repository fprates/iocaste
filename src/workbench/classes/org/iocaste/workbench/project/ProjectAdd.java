package org.iocaste.workbench.project;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.workbench.AbstractCommand;

public class ProjectAdd extends AbstractCommand {

    public ProjectAdd() {
        required("name");
        optional("text");
    }
    
    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        // TODO Auto-generated method stub
        
    }

}
