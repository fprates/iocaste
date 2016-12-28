package org.iocaste.packagetool.services.installers;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.globalconfig.common.GlobalConfig;
import org.iocaste.packagetool.common.GlobalConfigData;
import org.iocaste.packagetool.common.GlobalConfigItemData;
import org.iocaste.packagetool.services.Services;
import org.iocaste.packagetool.services.State;
import org.iocaste.protocol.Function;

public class GlobalConfigInstaller
        extends AbstractModuleInstaller<String, GlobalConfigData> {
    private GlobalConfig globalcfg;
    private Documents documents;
    
    public GlobalConfigInstaller(Services services) {
        super(services, "CONFIG_ENTRY");
    }

    @Override
    protected String getObjectName(String key, GlobalConfigData config) {
        return null;
    }

    @Override
    public void init(Function function) {
        globalcfg = new GlobalConfig(function);
        documents = new Documents(function);
    }

    @Override
    public void install(State state) throws Exception {
        super.installAll(state, state.data.getGlobalConfigs());
    }

    @Override
    protected void install(State state, String key, GlobalConfigData config) {
        for (GlobalConfigItemData item : config.getItens())
            globalcfg.define(state.pkgname, item.getName(), item.getType(),
                    item.getValue());
    }

    @Override
    public void remove(ExtendedObject object) {
        globalcfg.remove(getObjectName(object));
        documents.delete(object);
    }

    @Override
    public void update(State state) throws Exception {
        install(state);
    }

    @Override
    protected void update(State state, String key, GlobalConfigData object)
            throws Exception {
        // TODO Auto-generated method stub
        
    }
    
}

