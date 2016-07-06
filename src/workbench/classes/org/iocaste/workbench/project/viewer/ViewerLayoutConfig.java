package org.iocaste.workbench.project.viewer;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.shell.common.Const;

public class ViewerLayoutConfig {
    
    /*
     * - spec_name, spec_type, model
     * -- item_name, invisible, component_type, pick_action, vlength, disabled
     */
    public static final Map<String, Object[]> config;
    
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
        config.put("packages_items", new Object[] {
                "tt", "WB_JAVA_PACKAGE",
                new Object[][] {
                    {"PROJECT", true, null, null, 0, true},
                    {"PACKAGE", false, Const.LINK, "package_pick", 40, false}
                }
            });
        config.put("packages_detail", new Object[] {
                "dt", "WB_JAVA_PACKAGE",
                new Object[][] {
                    {"PROJECT", true, null, null, 0, true},
                    {"PACKAGE", false, null, null, 40, false}
                }
            });
        config.put("package_item_items", new Object[] {
                "tt", "WB_JAVA_CLASS",
                new Object[][] {
                    {"CLASS_ID", true, null, null, 0, true},
                    {"PROJECT", true, null, null, 0, true},
                    {"PACKAGE", true, null, null, 0, true},
                    {"NAME", false, Const.LINK, "source_pick", 40, false},
                    {"FULL_NAME", true, null, null, 0, true}
                }
            });
        config.put("package_item_detail", new Object[] {
                "dt", "WB_JAVA_CLASS",
                new Object[][] {
                    {"CLASS_ID", true, null, null, 0, true},
                    {"PROJECT", true, null, null, 0, true},
                    {"PACKAGE", true, null, null, 0, true},
                    {"NAME", false, null, null, 40, false},
                    {"FULL_NAME", true, null, null, 0, true}
                }
            });
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
        config.put("actions_items", new Object[] {
                "tt", "WB_SCREEN_ACTION",
                new Object[][] {
                    {"ACTION_ID", true, null, null, 0, true},
                    {"PROJECT", true, null, null, 0, true},
                    {"SCREEN", true, null, null, 0, true},
                    {"NAME", false, null, null, 0, true},
                    {"CLASS", false, null, null, 40, true},
                    {"TYPE", false, null, null, 0, true}
                }
            });
        config.put("actions_detail", new Object[] {
                "dt", "WB_SCREEN_ACTION",
                new Object[][] {
                    {"ACTION_ID", true, null, null, 0, true},
                    {"PROJECT", true, null, null, 0, true},
                    {"SCREEN", true, null, null, 0, true},
                    {"CLASS", false, null, null, 40, false}
                }
            });
    }

}
