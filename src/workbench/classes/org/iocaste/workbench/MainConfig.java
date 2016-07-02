package org.iocaste.workbench;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.tiles.TilesData;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.workbench.project.ProjectsTileConfig;
import org.iocaste.workbench.project.ProjectsTileInput;
import org.iocaste.workbench.project.ProjectsTileSpec;

public class MainConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        TilesData tiles;
        InputComponent command = getElement("command");
        
        getNavControl().setTitle("iocaste.workbench");
        
        tiles = getTool("projects");
        tiles.spec = new ProjectsTileSpec();
        tiles.config = new ProjectsTileConfig();
        tiles.input = new ProjectsTileInput();
        
        context.view.setFocus(command);
        command.setObligatory(true);
        command.setLength(256);
        command.setVisibleLength(64);
    }

}
