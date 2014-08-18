package org.iocaste.datadict;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.shell.common.DataForm;

public class StructureConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        DataForm dataform;
        TableToolData tabletool;
        
        dataform = getElement("head");
        dataform.importModel("MODEL", context.function);
        
        tabletool = getTableTool("items");
        tabletool.model = "MODELITEM";
        tabletool.mode = TableTool.DISPLAY;
    }

}
