package org.iocaste.packagetool.services;

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
                globalcfg.define(state.pkgname, name, item.getType(),
                        item.getValue());
            }
        
        Registry.add(state.pkgname, "CONFIG_ENTRY", state);
    }
}
