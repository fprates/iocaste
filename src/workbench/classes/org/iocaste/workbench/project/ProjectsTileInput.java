package org.iocaste.workbench.project;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.tiles.AbstractTileInput;

public class ProjectsTileInput extends AbstractTileInput {

    @Override
    protected void execute(PageBuilderContext context) { }

    @Override
    protected void init(PageBuilderContext context) {
        ProjectInfo project = get();
        textset("item", "");
        textset("name", project.name);
        textset("title", project.title);
        linkadd("item", "PROJECT", project.name);
    }
    
}
