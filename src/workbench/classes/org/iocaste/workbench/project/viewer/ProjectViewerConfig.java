package org.iocaste.workbench.project.viewer;

import org.iocaste.appbuilder.common.AbstractComponentData;
import org.iocaste.appbuilder.common.AbstractComponentDataItem;
import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolData;
import org.iocaste.appbuilder.common.tabletool.TableToolData;

public class ProjectViewerConfig extends AbstractViewConfig {
    private static final Object[][] MODELS = {
            {
                "data_elements_items", "tt", "WB_DATA_ELEMENTS",
                new Object[][] {
                        {"PROJECT", true} 
                    },
            },
            {"models_items", "tt", "WB_MODEL_HEADER", null},
            {"views_items", "tt", "WB_SCREEN_HEADER", null},
            {"links_items", "tt", "WB_LINKS", null},
            {"source_items", "tt", "WB_JAVA_CLASS", null},
            {
                "data_elements_detail", "dt", "WB_DATA_ELEMENTS",
                new Object[][] {
                        {"PROJECT", true}
                    },
            },
            {"links_detail", "dt", "WB_LINKS", null}
    };
    
    @Override
    protected void execute(PageBuilderContext context) {
        Object[][] items;
        AbstractComponentData data;
        AbstractComponentDataItem item;
        TableToolData ttdata;
        DataFormToolData dtdata;
        
        for (int i = 0; i < MODELS.length; i++) {
            data = getTool((String)MODELS[i][0]);
            data.model = (String)MODELS[i][2];
            switch ((String)MODELS[i][1]) {
            case "tt":
                data.disabled = true;
                data.style = "wb_tt_viewer";
                ttdata = (TableToolData)data;
                ttdata.mark = true;
                break;
            case "dt":
                getElement((String)MODELS[i][0]).setStyleClass("wb_dt_viewer");
                break;
            }
            
            items = (Object[][])MODELS[i][3];
            if (items != null) {
                for (int j = 0; j < items.length; j++) {
                    item = data.instance((String)items[j][0]);
                    item.invisible = (boolean)items[j][1];
                }
            }
        }
    }
    
}

