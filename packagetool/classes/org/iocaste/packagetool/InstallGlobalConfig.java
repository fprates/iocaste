package org.iocaste.packagetool;

import java.util.Set;

import org.iocaste.globalconfig.common.GlobalConfig;
import org.iocaste.packagetool.common.GlobalConfigData;
import org.iocaste.packagetool.common.GlobalConfigItemData;

public class InstallGlobalConfig {
    
    public static final void init(Set<GlobalConfigData> configs, State state) {
        String name;
        GlobalConfig globalcfg = new GlobalConfig(state.function);
        
        for (GlobalConfigData config : configs)
            for (GlobalConfigItemData item : config.getItens()) {
                name = item.getName();
                globalcfg.define(name, item.getType(), item.getValue());
                Registry.add(name, "CONFIG_ENTRY", state);
            }
    }
}
