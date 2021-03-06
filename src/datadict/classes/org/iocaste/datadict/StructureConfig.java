package org.iocaste.datadict;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolData;
import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolColumn;
import org.iocaste.appbuilder.common.tabletool.TableToolData;

public class StructureConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        DataFormToolData dataform;
        TableToolData tabletool;
        TableToolColumn column;
        Context extcontext = getExtendedContext();
        
        dataform = getTool("head");
        dataform.custommodel = extcontext.model;
        dataform.disabled = true;
        show(dataform, "NAME", "TABLE");
        
        tabletool = getTool("items");
        tabletool.model = "DD_MODEL_ITEM";
        tabletool.mode = TableTool.DISPLAY;
        tabletool.vlines = 0;
        
        column = tabletool.instance("NAME");
        column.length = 24;
    }

}
