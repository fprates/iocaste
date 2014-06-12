package org.iocaste.workbench;

import java.io.File;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class ProjectCreate extends AbstractActionHandler {

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        Context extcontext = getExtendedContext();
        
        extcontext.project = getdfst("head", "PROJECT_ID");
        extcontext.projectdir = new StringBuilder(extcontext.repository).
                append(File.separator).
                append(extcontext.project).toString();
        
        extcontext.views.clear();
        redirect(Main.PRJC);
    }

}
