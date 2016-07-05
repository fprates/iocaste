package org.iocaste.workbench.project.viewer;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.AbstractComponentData;
import org.iocaste.appbuilder.common.AbstractComponentDataItem;
import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.shell.common.Const;

public class ProjectViewerConfig extends AbstractViewConfig {
    
    /*
     * - spec_name, spec_type, model
     * -- item_name, invisible, component_type, pick_action, vlength, disabled
     */
    private static final Map<String, Object[]> config;
    
    static {
        config = new HashMap<>();
        config.put("data_elements_items", new Object[] {
                "tt", "WB_DATA_ELEMENTS",
                new Object[][] {
                        {"PROJECT", true, null, null, 0, true},
                        {"NAME", false, null, null, 0, true},
                        {"TYPE", false, null, null, 0, true},
                        {"SIZE", false, null, null, 0, true},
                        {"DECIMALS", false, null, null, 0, true},
                        {"UPCASE", false, null, null, 0, true}
                    }
                });
        config.put("data_elements_detail", new Object[] {
                "dt", "WB_DATA_ELEMENTS",
                new Object[][] {
                        {"PROJECT", true, null, null, 0, true},
                    }
                });
        config.put("models_items", new Object[] {
                "tt", "WB_MODEL_HEADER",
                new Object[][] {
                        {"PROJECT", true, null, null, 0, true},
                        {"NAME", false, Const.LINK, "model_pick", 0, false},
                        {"TABLE", false, null, null, 0, true}
                    }
                });
        config.put("models_detail", new Object[] {
                "dt", "WB_MODEL_HEADER",
                new Object[][] {
                        {"PROJECT", true, null, null, 0, true}
                    }
                });
        config.put("views_items", new Object[] {
                "tt", "WB_SCREEN_HEADER",
                new Object[][] {
                        {"PROJECT", true, null, null, 0, true},
                        {"NAME", false, Const.LINK, "view_pick", 0, false}
                    }
                });
        config.put("views_detail", new Object[] {
                "dt", "WB_SCREEN_HEADER",
                new Object[][] {
                        {"PROJECT", true, null, null, 0, true}
                    }
                });
        config.put("links_items", new Object[] {
                "tt", "WB_LINKS",
                new Object[][] {
                        {"PROJECT", true, null, null, 0, true},
                        {"LINK_ID", true, null, null, 0, true},
                        {"NAME", false, null, null, 0, true},
                        {"COMMAND", false, null, null, 40, true},
                        {"GROUP", false, null, null, 0, true},
                    }
                });
        config.put("links_detail", new Object[] {
                "dt", "WB_LINKS",
                new Object[][] {
                        {"PROJECT", true, null, null, 0, true},
                        {"LINK_ID", true, null, null, 0, true},
                        {"COMMAND", false, null, null, 40, false},
                    }
                });
        config.put("source_items", new Object[] {
                "tt", "WB_JAVA_CLASS", null});
        config.put("model_item_items", new Object[] {
                "tt", "WB_MODEL_ITEMS",
                new Object[][] {
                        {"ITEM_ID", true, null, null, 0, true},
                        {"PROJECT", true, null, null, 0, true},
                        {"MODEL", true, null, null, 0, true},
                        {"NAME", false, null, null, 0, true},
                        {"FIELD", false, null, null, 0, true},
                        {"DATA_ELEMENT", false, null, null, 0, true},
                        {"KEY", false, null, null, 0, true}
                    }
                });
        config.put("model_item_detail", new Object[] {
                "dt", "WB_MODEL_ITEMS",
                new Object[][] {
                        {"ITEM_ID", true, null, null, 0, true},
                        {"PROJECT", true, null, null, 0, true},
                        {"MODEL", true, null, null, 0, true}
                    }
                });
        config.put("view_item_items", new Object[] {
                "tt", "WB_SCREEN_SPEC",
                new Object[][] {
                        {"ITEM_ID", true, null, null, 0, true},
                        {"PROJECT", true, null, null, 0, true},
                        {"SCREEN", true, null, null, 0, true},
                        {"PARENT", false, null, null, 0, true},
                        {"NAME", false, Const.LINK, "spec_pick", 0, false},
                        {"TYPE", false, null, null, 0, true}
                    }
                });
        config.put("view_item_detail", new Object[] {
                "dt", "WB_SCREEN_SPEC",
                new Object[][] {
                        {"ITEM_ID", true, null, null, 0, true},
                        {"PROJECT", true, null, null, 0, true},
                        {"SCREEN", true, null, null, 0, true},
                        {"PARENT", false, null, null, 0, false},
                        {"NAME", false, null, null, 0, false},
                        {"TYPE", false, null, null, 0, false}
                    }
                });
        config.put("spec_config_items", new Object[] {
                "tt", "WB_SCREEN_CONFIG",
                new Object[][] {
                        {"CONFIG_ID", true, null, null, 0, true},
                        {"PROJECT", true, null, null, 0, true},
                        {"SCREEN", true, null, null, 0, true},
                        {"SPEC", true, null, null, 0, true},
                        {"NAME", false, null, null, 0, true},
                        {"VALUE", false, null, null, 40, true},
                        {"TYPE", true, null, null, 0, true}
                    }
                });
        config.put("spec_config_detail", new Object[] {
                "dt", "WB_SCREEN_CONFIG",
                new Object[][] {
                        {"CONFIG_ID", true, null, null, 0, true},
                        {"PROJECT", true, null, null, 0, true},
                        {"SCREEN", true, null, null, 0, true},
                        {"SPEC", true, null, null, 0, true},
                        {"NAME", false, null, null, 0, false},
                        {"VALUE", false, null, null, 40, false},
                        {"TYPE", true, null, null, 0, true}
                    }
                });
    }
    
    private final void itemconfig(String name, Object[] dataconfig) {
        Object[][] items;
        AbstractComponentData data;
        AbstractComponentDataItem item;
        TableToolData ttdata;
        
        data = getTool(name);
        if (data == null)
            return;
        data.model = (String)dataconfig[1];
        ttdata = null;
        switch ((String)dataconfig[0]) {
        case "tt":
            ttdata = (TableToolData)data;
            ttdata.mark = true;
            ttdata.style = "wb_tt_viewer";
            break;
        case "dt":
            getElement(name).setStyleClass("wb_dt_viewer");
            break;
        }
        
        items = (Object[][])dataconfig[2];
        if (items == null)
            return;
        for (int j = 0; j < items.length; j++) {
            item = data.instance((String)items[j][0]);
            item.invisible = (boolean)items[j][1];
            item.vlength = (int)items[j][4];
            item.disabled = (boolean)items[j][5];
            if (items[j][2] != null) {
                item.componenttype = (Const)items[j][2];
                item.action = (String)items[j][3];
            }
        }
    }
    
    @Override
    protected void execute(PageBuilderContext context) {
        for (String name : config.keySet())
            itemconfig(name, config.get(name));
    }
}

