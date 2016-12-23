package org.iocaste.dataeditor.edit;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.dataeditor.TableConfig;
import org.iocaste.dataeditor.display.DisplayConfig;

public class EditConfig extends DisplayConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        TableConfig config = new TableConfig();
        
        config.context = context;
        config.mode = TableTool.UPDATE;
        config.mark = true;
        configTable(config);

    }

}
