package org.iocaste.workbench.project;

import org.iocaste.appbuilder.common.AbstractViewInput;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class ProjectsTileInput extends AbstractViewInput {

    @Override
    protected void execute(PageBuilderContext context) { }

    @Override
    protected void init(PageBuilderContext context) {
        ProjectInfo project = tilesobjectget();
        textset("item", "");
        textset("name", project.name);
        textset("title", project.title);
        linkadd("item", "PROJECT", project.name);
    }
    
}
