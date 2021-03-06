package org.iocaste.packagetool.services.installers;

import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.packagetool.services.Services;
import org.iocaste.packagetool.services.State;
import org.iocaste.protocol.Function;

public class ComplexModelInstaller
        extends AbstractModuleInstaller<String, ComplexModel> {
    private Documents documents;
    
    public ComplexModelInstaller(Services services) {
        super(services, "CMODEL");
    }

    @Override
    protected final String getObjectName(String key, ComplexModel cmodel) {
        return cmodel.getName();
    }

    public final void init(Function function) {
        documents = new Documents(function);
    }
    
    @Override
    public final void install(State state) throws Exception {
        super.installAll(state, state.data.getCModels());
    }

    @Override
    protected final void install(State state, String key, ComplexModel cmodel) {
        if (documents.getComplexModel(getObjectName(key, cmodel)) == null)
            documents.create(cmodel);
    }

    @Override
    public final void remove(ExtendedObject object) {
        documents.removeComplexModel(getObjectName(object));
        documents.delete(object);
    }

    @Override
    public final void update(State state) throws Exception {
        super.updateAll(state, state.data.getCModels());
    }

    @Override
    protected final void update(State state, String key, ComplexModel cmodel)
            throws Exception {
        String name = cmodel.getName();
        if (documents.getComplexModel(name) != null)
            documents.update(cmodel);
    }
    
}

