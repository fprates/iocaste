package org.iocaste.workbench.project.viewer;

import org.iocaste.appbuilder.common.AbstractComponentData;
import org.iocaste.appbuilder.common.AbstractComponentDataItem;
import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolData;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.shell.common.Const;

public class ProjectViewerConfig extends AbstractViewConfig {
    /*
     * - spec_name, spec_type, model
     * -- item_name, invisible, component_type, pick_action, vlength
     */
    private static final Object[][] MODELS = {
            {
                "data_elements_items", "tt", "WB_DATA_ELEMENTS",
                new Object[][] {
                        {"PROJECT", true, null, null, 0}
                    },
            },
            {
                "models_items", "tt", "WB_MODEL_HEADER",
                new Object[][] {
                        {"PROJECT", true, null, null, 0},
                        {"NAME", false, Const.LINK, "model_pick", 0}
                    },
            },
            {"views_items", "tt", "WB_SCREEN_HEADER",
                new Object[][] {
                        {"PROJECT", true, null, null, 0},
                        {"NAME", false, Const.LINK, "view_pick", 0}
                    },
            },
            {"links_items", "tt", "WB_LINKS",
                new Object[][] {
                        {"PROJECT", true, null, null, 0},
                        {"LINK_ID", true, null, null, 0}
                    },
            },
            {"source_items", "tt", "WB_JAVA_CLASS", null},
            {
                "data_elements_detail", "dt", "WB_DATA_ELEMENTS",
                new Object[][] {
                        {"PROJECT", true, null, null, 0}
                    },
            },
            {
                "models_detail", "dt", "WB_MODEL_HEADER",
                new Object[][] {
                        {"PROJECT", true, null, null, 0}
                    },
            },
            {
                "views_detail", "dt", "WB_SCREEN_HEADER",
                new Object[][] {
                        {"PROJECT", true, null, null, 0}
                    },
            },
            {"links_detail", "dt", "WB_LINKS",
                new Object[][] {
                        {"PROJECT", true, null, null, 0},
                        {"LINK_ID", true, null, null, 0},
                        {"COMMAND", false, null, null, 40}
                    },
            }
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
            ttdata = null;
            dtdata = null;
            switch ((String)MODELS[i][1]) {
            case "tt":
                data.style = "wb_tt_viewer";
                ttdata = (TableToolData)data;
                ttdata.mark = true;
                break;
            case "dt":
                getElement((String)MODELS[i][0]).setStyleClass("wb_dt_viewer");
                break;
            }
            
            items = (Object[][])MODELS[i][3];
            if (items == null)
                continue;
            for (int j = 0; j < items.length; j++) {
                item = data.instance((String)items[j][0]);
                item.invisible = (boolean)items[j][1];
                item.vlength = (int)items[j][4];
                if (items[j][2] != null) {
                    item.componenttype = (Const)items[j][2];
                    item.action = (String)items[j][3];
                    if ((item.componenttype != Const.LINK) && (ttdata != null))
                        item.disabled = true;
                }
            }
        }
    }
}

