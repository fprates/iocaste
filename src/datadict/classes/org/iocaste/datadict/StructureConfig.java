package org.iocaste.datadict;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolColumn;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.shell.common.DataForm;

public class StructureConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        DataForm dataform;
        TableToolData tabletool;
        TableToolColumn column;
        
        dataform = getElement("head");
        dataform.importModel("MODEL", context.function);
        dataform.setEnabled(false);
        
        tabletool = getTableTool("items");
        tabletool.model = "DD_MODEL_ITEM";
        tabletool.mode = TableTool.DISPLAY;
        
        column = new TableToolColumn(tabletool, "NAME");
        column.size = 24;
    }

}
