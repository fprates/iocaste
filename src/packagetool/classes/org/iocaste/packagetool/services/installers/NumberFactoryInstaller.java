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
    protected String getObjectName(Map<String, Long> object) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void init(Function function) {
        if (documents == null)
            documents = new Documents(function);
    }

    @Override
    public void install(State state) throws Exception {
        Map<String, Map<String, Long>> numbers;
        Map<String, Long> factory;
        
        numbers = state.data.getNumberFactories();
        for (String key : numbers.keySet()) {
            documents.createNumberFactory(key, null, factory=numbers.get(key));
            registry(state, factory);
        }
    }

    @Override
    protected void install(State state, Map<String, Long> factory) { }

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
    protected void update(State state, Map<String, Long> object)
            throws Exception { }
    
}

