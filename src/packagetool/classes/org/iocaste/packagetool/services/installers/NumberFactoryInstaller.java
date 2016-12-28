package org.iocaste.packagetool.services.installers;

import java.util.Map;

import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.packagetool.services.Services;
import org.iocaste.packagetool.services.State;
import org.iocaste.protocol.Function;

public class NumberFactoryInstaller extends
        AbstractModuleInstaller<String, Map<String, Long>> {
    private Documents documents;
    
    public NumberFactoryInstaller(Services services) {
        super(services, "NUMBER");
    }

    @Override
    protected String getObjectName(String key, Map<String, Long> object) {
        return key;
    }

    @Override
    public void init(Function function) {
        documents = new Documents(function);
    }

    @Override
    public void install(State state) throws Exception {
        super.installAll(state, state.data.getNumberFactories());
    }

    @Override
    protected void install(State state, String key, Map<String, Long> factory) {
        documents.createNumberFactory(key, null, factory);
    }

    @Override
    public final void remove(ExtendedObject object) {
        documents.removeNumberFactory(getObjectName(object));
        documents.delete(object);
    }

    @Override
    public void update(State state) throws Exception {
        install(state);
    }

    @Override
    protected void update(State state, String key, Map<String, Long> object) { }
    
}

